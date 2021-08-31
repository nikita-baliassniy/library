package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private String title;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_per_book")
    private double pricePerBook;

    @Column(name = "price")
    private double price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CartItem(Book book) {
        this.book = book;
        this.quantity = 1;
        this.pricePerBook = book.getPrice();
        this.price = this.pricePerBook;
    }

    public void incrementQuantity() {
        quantity++;
        price = quantity * pricePerBook;
    }

    public void decrementQuantity() {
        quantity--;
        price = quantity * pricePerBook;
    }
}
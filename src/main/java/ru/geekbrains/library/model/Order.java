package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "total_price")
    private double price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

//    @Column(name = "address")
//    private String address;

    public Order(Cart cart, User user) {
        this.price = cart.getPrice();
        this.owner = user;
        this.items = new ArrayList<>();
//        this.address = address;
        for (CartItem item : cart.getItems()) {
            OrderItem oi = new OrderItem(item);
            oi.setOrder(this);
            this.items.add(oi);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order: ")
                .append(owner.getEmail())
                .append(", ")
                .append("items: ");
        for (OrderItem item : items) {
            sb.append(item.getId())
                    .append(", ");
        }

        return sb.toString();
    }
}

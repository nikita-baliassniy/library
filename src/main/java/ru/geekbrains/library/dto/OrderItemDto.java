package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.OrderItem;

@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long id;
    private String bookTitle;
    private int quantity;
    private double pricePerProduct;
    private double price;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getBook().getId();
        this.bookTitle = orderItem.getBook().getTitle();
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerBook();
        this.price = orderItem.getPrice();
    }
}

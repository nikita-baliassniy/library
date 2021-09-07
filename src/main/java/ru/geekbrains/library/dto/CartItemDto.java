package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.CartItem;

@NoArgsConstructor
@Data
public class CartItemDto {
    private Long bookId;
    private String bookTitle;
    private String cover;
    private int quantity;
    private double pricePerBook;
    private double price;

    public CartItemDto(CartItem cartItem) {
        this.bookId = cartItem.getBook().getId();
        this.bookTitle = cartItem.getBook().getTitle();
        this.cover = cartItem.getBook().getBookStorage().getLink_cover();
        this.quantity = cartItem.getQuantity();
        this.pricePerBook = cartItem.getPricePerBook();
        this.price = cartItem.getPrice();
    }
}

package ru.geekbrains.library.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super("Корзина не найдена!");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}

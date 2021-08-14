package ru.geekbrains.library.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Заказ не найден!");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}

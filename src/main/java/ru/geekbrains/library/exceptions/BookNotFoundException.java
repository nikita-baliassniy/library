package ru.geekbrains.library.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Книга не найдена!");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}

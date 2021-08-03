package ru.geekbrains.library.exceptions;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException() {
        super("Автор не найден");
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}

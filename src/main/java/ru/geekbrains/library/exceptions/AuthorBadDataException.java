package ru.geekbrains.library.exceptions;

public class AuthorBadDataException extends RuntimeException{

    public AuthorBadDataException() {
        super("Не правильно заполнены поля");
    }

    public AuthorBadDataException(String message) {
        super(message);
    }
}

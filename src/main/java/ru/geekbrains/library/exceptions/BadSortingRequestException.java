package ru.geekbrains.library.exceptions;

public class BadSortingRequestException extends RuntimeException {
    public BadSortingRequestException(String message) {
        super(message);
    }
}

package ru.geekbrains.library.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("Пользователь не найден");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

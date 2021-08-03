package ru.geekbrains.library.exceptions;

public class GenreBadDataException extends RuntimeException{

    public GenreBadDataException() {
        super("Ошибка сохранения данных Автора");
    }

    public GenreBadDataException(String message) {
        super(message);
    }
}

package ru.geekbrains.library.exceptions;

public class BookBadDataException extends RuntimeException{

    public BookBadDataException() {
        super("Не правильные данные книги");
    }

    public BookBadDataException(String msg) {
        super(msg);
    }
}

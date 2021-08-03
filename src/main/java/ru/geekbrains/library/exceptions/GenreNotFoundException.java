package ru.geekbrains.library.exceptions;

public class GenreNotFoundException extends RuntimeException{

    public GenreNotFoundException() {
        super("Автор не найден");
    }

    public GenreNotFoundException(String message) {
        super(message);
    }


}

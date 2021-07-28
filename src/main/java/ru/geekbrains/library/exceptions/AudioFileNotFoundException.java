package ru.geekbrains.library.exceptions;

public class AudioFileNotFoundException extends RuntimeException {

    public AudioFileNotFoundException() {
        super("Аудио файл не найден!");
    }

    public AudioFileNotFoundException(String message) {
        super(message);
    }
}

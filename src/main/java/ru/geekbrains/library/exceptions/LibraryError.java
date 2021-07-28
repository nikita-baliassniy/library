package ru.geekbrains.library.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class LibraryError {

    private int errorCode;
    private String message;
    private Date timeStamp;

    public LibraryError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timeStamp = new Date();
    }
}

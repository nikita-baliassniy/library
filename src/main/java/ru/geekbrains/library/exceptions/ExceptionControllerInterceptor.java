package ru.geekbrains.library.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionControllerInterceptor {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AudioFileNotFoundException.class)
    public ResponseEntity<?> handleAudioFileNotFoundException(AudioFileNotFoundException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookBadDataException.class)
    public ResponseEntity<?> handleBookBadDataException(BookBadDataException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<?> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorBadDataException.class)
    public ResponseEntity<?> handleAuthorBadDataException(AuthorBadDataException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleAuthorNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadSortingRequestException.class)
    public ResponseEntity<?> handleBadSortingRequestException(BadSortingRequestException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PutDiscountException.class)
    public ResponseEntity<?> handlePutDiscountException(PutDiscountException ex) {
        log.error(ex.getMessage());
        LibraryError error = new LibraryError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

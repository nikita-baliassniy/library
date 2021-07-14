package ru.geekbrains.library.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.library.dto.BookDto;
import ru.geekbrains.library.exceptions.ResourceNotFoundException;
import ru.geekbrains.library.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookDtoById(id).orElseThrow(() ->
                new ResourceNotFoundException("There is no book with id " + id));
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.findAll();
    }
}

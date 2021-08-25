package ru.geekbrains.library.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.BookDto;
import ru.geekbrains.library.dto.BookListDto;
import ru.geekbrains.library.dto.CommentDto;
import ru.geekbrains.library.exceptions.BookBadDataException;
import ru.geekbrains.library.exceptions.BookNotFoundException;
import ru.geekbrains.library.exceptions.GenreNotFoundException;
import ru.geekbrains.library.model.Genre;
import ru.geekbrains.library.model.filter.ModelSorter;
import ru.geekbrains.library.repositories.specifications.BookSpecifications;
import ru.geekbrains.library.services.BookService;
import ru.geekbrains.library.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookDtoById(id).orElseThrow(() ->
                new BookNotFoundException("Книга с ID:  " + id + " не найдена"));
    }

    @GetMapping
    public Page<BookListDto> getAllBooks(@RequestParam MultiValueMap<String, String> params,
                                         @RequestParam(defaultValue = "1", name = "page") Integer page,
                                         @RequestParam(defaultValue = "9", name = "count") Integer count) {
        Page<BookListDto> bld = bookService.getBookPage(BookSpecifications.build(params), page, count, new ModelSorter(params));
        return bld;
    }

    @GetMapping("/similar/{id}")
    public List<BookListDto> getSimilarBooks(@PathVariable Long id) {
        return bookService.getSimilarBooks(id, 2);
    }

    @GetMapping("/recommend/{userId}")
    public List<BookListDto> getRecommendations(@PathVariable Long userId) {
        return bookService.getRecommendations(userId, 3.5, 2);
    }

    @PostMapping
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookService.insertOrUpdateBook(bookDto).orElseThrow(() -> new BookBadDataException("Ошибка сохранения Книги"));
    }

    @PutMapping
    public BookDto insertBook(@RequestBody BookDto bookDto) {
        return bookService.insertOrUpdateBook(bookDto).orElseThrow(() -> new BookBadDataException("Ошибка создания Книги"));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        if (bookService.deleteById(id) <= 0) {
            throw new BookNotFoundException("Ошибка удаления книги с ID: " + id + ". Ткая книга не найдена.");
        }
    }

    @PutMapping("/{bookId}/comment")
    public BookDto addNewComment(@RequestBody CommentDto commentDto, @PathVariable Long bookId) {
        return bookService.addComment(commentDto, bookId).orElseThrow(() -> new BookBadDataException("Ошибка сохранения комментария"));
    }

    @GetMapping("/genre/{id}")
    public Page<BookListDto> getBooksByGenre(@PathVariable Long id,
                                             @RequestParam(defaultValue = "1", name = "page") Integer page,
                                             @RequestParam(defaultValue = "10", name = "count") Integer count) {
        Genre genre = genreService.getGenreById(id).orElseThrow(()-> new GenreNotFoundException("Жанр с Id: " + " не найлен"));
        return bookService.getBookPageByGenre(genre, page, count);
    }
}

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
import ru.geekbrains.library.repositories.specifications.BookSpecifications;
import ru.geekbrains.library.services.BookService;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookDtoById(id).orElseThrow(() ->
                new BookNotFoundException("Книга с ID:  " + id + " не найдена"));
    }

    @GetMapping
    public Page<BookListDto> getAllBooks(@RequestParam MultiValueMap<String, String> params,
                                         @RequestParam(defaultValue = "0", name = "page") Integer page,
                                         @RequestParam(defaultValue = "10", name = "count") Integer count) {
        Page<BookListDto> bld = bookService.getBookPage(BookSpecifications.build(params), page, count);
        return bld;
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
}

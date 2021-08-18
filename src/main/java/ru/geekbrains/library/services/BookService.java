package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.BookDto;
import ru.geekbrains.library.dto.BookListDto;
import ru.geekbrains.library.dto.CommentDto;
import ru.geekbrains.library.exceptions.BookNotFoundException;
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.model.Comments;
import ru.geekbrains.library.model.Genre;
import ru.geekbrains.library.repositories.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    public Optional<BookDto> findBookDtoById(Long id) {
        return bookRepository.findBookById(id).map(book -> modelMapper.map(book, BookDto.class));
    }

    public List<BookListDto> findAll() {
        return bookRepository
                .findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookListDto.class))
                .collect(Collectors.toList());
    }

    public Page<BookListDto> getBookPage(Specification<Book> specification, Integer page, Integer count) {
        return bookRepository
                .findAll(specification, PageRequest.of(page-1, count))
                .map(book -> modelMapper.map(book, BookListDto.class));
    }

    public Optional<BookDto> insertOrUpdateBook(BookDto bookDto) {
        try {
//            Book upBook;
            Book book = modelMapper.map(bookDto, Book.class);
            if (book.getId() == null) {
                book.getBookInfo().setBook(book);
                book.getBookStorage().setBook(book);
            }
//            upBook = bookRepository.save(book);
            return Optional.of(modelMapper.map(bookRepository.save(book), BookDto.class));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<BookDto> addComment(CommentDto commentDto, Long bookId) {
        Book book = bookRepository.findBookById(bookId).get();
        book.addCommentAndRecalcScore(modelMapper.map(commentDto, Comments.class));
        return Optional.of(modelMapper.map(bookRepository.save(book), BookDto.class));
    }

    public Integer deleteById(Long id) {
        return bookRepository.deleteBookById(id);
    }

    public List<BookListDto> getSimilarBookPage(Long id, Integer minimumSimilarGenres) {
        Map<BookListDto, Integer> similarBooks = new HashMap<>();
        Book exampleBook = bookRepository.findBookById(id).get();
        Set<String> genresForSearch = exampleBook
                .getGenres()
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
        bookRepository
                .findAll()
                .stream()
                .filter(b -> !b.getId().equals(id))
                .forEach(b -> {
                    Set<String> inter = b
                            .getGenres()
                            .stream()
                            .map(Genre::getName)
                            .collect(Collectors.toSet());
                    inter.retainAll(genresForSearch);
                    if (inter.size() >= minimumSimilarGenres) {
                        similarBooks.put(modelMapper.map(b, BookListDto.class), inter.size());
                    }
                });
        return similarBooks
                .entrySet()
                .stream()
                .sorted(Map.Entry.<BookListDto, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}

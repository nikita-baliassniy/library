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
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

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
                .findAll(specification, PageRequest.of(page, count))
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

    public Integer deleteById(Long id) {
        return bookRepository.deleteBookById(id);
    }


}

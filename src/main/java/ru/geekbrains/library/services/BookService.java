package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.BookDto;
import ru.geekbrains.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Optional<BookDto> findBookDtoById(Long id) {
        return bookRepository.findById(id).map(BookDto::new);
    }

    public List<BookDto> findAll() {
        List<BookDto> books = new ArrayList<>();
        bookRepository.findAll().forEach(b -> books.add(new BookDto(b)));
        return books;
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}

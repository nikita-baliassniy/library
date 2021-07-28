package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.BookDto;
import ru.geekbrains.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Optional<BookDto> findBookDtoById(Long id) {
        return bookRepository.findById(id).map(BookDto::new);
    }

    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}

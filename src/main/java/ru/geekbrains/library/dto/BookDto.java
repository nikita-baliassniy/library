package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.Author;
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private int yearOfPublish;
    private List<String> genres;
    private List<String> authors;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.description = book.getDescription();
        this.yearOfPublish = book.getYearOfPublish();
        this.genres = book.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
        this.authors = book.getAuthors().stream().map(Author::getName).collect(Collectors.toList());
    }
}

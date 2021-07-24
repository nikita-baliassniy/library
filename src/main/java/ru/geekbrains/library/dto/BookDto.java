package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.Book;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Double price;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
    }
}

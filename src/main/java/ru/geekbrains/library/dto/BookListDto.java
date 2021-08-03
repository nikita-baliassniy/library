package ru.geekbrains.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class BookListDto {

    private Long id;
    private String title;
    private double price;
    private String description;
    private int yearOfPublish;
    private Integer commentsCount;
    private BookInfoDto bookInfo;
    private List<AuthorListDto> authors;
    private List<GenreListDto> genres;

//    public BookListDto(Book book) {
//        this.id = book.getId();
//        this.title = book.getTitle();
//        this.price = book.getPrice();
//        this.description = book.getDescription();
//        this.yearOfPublish = book.getYearOfPublish();
//        this.authors = book.getAuthors().stream().map(AuthorListDto::new).collect(Collectors.toList());
//        this.bookInfo = new BookInfoDto(book.getBookInfo());
////        this.bookStorage = new BookStorageDto(book.getBookStorage());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BookListDto tmp = (BookListDto) o;
        return id.equals(tmp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

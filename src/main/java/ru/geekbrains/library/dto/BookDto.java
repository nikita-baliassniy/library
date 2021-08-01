package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private int yearOfPublish;
    private BookInfoDto bookInfo;
    private BookStorageDto bookStorage;
    private List<AuthorListDto> authors;
    private List<GenreDto> genres;
    private List<CommentDto> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BookDto tmp = (BookDto) o;
        return o.equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

package ru.geekbrains.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("link_cover")
    private String bookStorageLink_cover;
    private List<AuthorListDto> authors;
    private List<GenreListDto> genres;

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

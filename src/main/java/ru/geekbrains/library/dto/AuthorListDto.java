package ru.geekbrains.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.library.model.Author;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class AuthorListDto {

    private Long id;
    private String name;

    public AuthorListDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    @Override
    public String toString() {
        return "AuthorListDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorListDto that = (AuthorListDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

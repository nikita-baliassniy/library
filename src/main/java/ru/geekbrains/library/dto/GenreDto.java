package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenreDto {

    private Long id;
    private String name;
    private List<BookListDto> books;

}

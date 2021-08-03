package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorDto {

    private Long id;
    private String name;
    private Date dateOfBirth;
    private String biography;
    private String country;
    private List<BookListDto> books;


}

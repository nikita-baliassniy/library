package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDto {

    private Long id;
    private String email;

}

package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterDto {

    private String name;
    private String email;
    private String password;

}

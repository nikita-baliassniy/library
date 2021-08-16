package ru.geekbrains.library.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}

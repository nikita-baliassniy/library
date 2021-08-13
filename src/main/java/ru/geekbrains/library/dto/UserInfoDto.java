package ru.geekbrains.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfoDto {
    private String phone;
    private int discount;
    private String address;
    private LocalDate birthday;
}

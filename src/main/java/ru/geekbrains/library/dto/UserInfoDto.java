package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private String name;
    private String phone;
    private int discount;
    private String address;
    private LocalDate birthday;
}

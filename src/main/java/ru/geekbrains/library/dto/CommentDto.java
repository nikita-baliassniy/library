package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
//    private Book book;
    private UserListDto user;
    private String text;
    private Integer score;
    private LocalDateTime createdAt;
}

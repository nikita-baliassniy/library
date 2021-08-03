package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
//    private Book book;
//    private User user;
    private String text;
    private LocalDateTime createdAt;
}

package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.library.model.BookInfo;

@Data
@NoArgsConstructor
public class BookInfoDto {
    private Long id;
    private Integer size;
    private double score;
    private Integer age_recommendation;

//    public BookInfoDto(BookInfo bookInfo) {
//        this.id = bookInfo.getId();
//        this.size = bookInfo.getSize();
//        this.score = bookInfo.getScore();
//        this.age_recommendation = bookInfo.getAge_recommendation();
//    }
}

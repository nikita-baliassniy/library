package ru.geekbrains.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookStorageDto {

    private Long id;
    private String link_cover;
    private String link_fb2;
    private String link_pdf;
    private String link_doc;
    private String link_audio;

//    public BookStorageDto(BookStorage bookStorage) {
//        this.id = bookStorage.getId();
//        this.link_cover = bookStorage.getLink_cover();
//        this.link_fb2 = bookStorage.getLink_fb2();
//        this.link_pdf = bookStorage.getLink_pdf();
//        this.link_doc = bookStorage.getLink_doc();
//        this.link_audio = bookStorage.getLink_audio();
//    }
}

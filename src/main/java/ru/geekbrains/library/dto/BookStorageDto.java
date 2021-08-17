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
    private String link_epub;
    private String link_audio;
}

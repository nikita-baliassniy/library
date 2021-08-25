package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "books_storage")
public class BookStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "link_cover", length = 255)
    private String link_cover;

    @Column(name = "link_fb2", length = 255)
    private String link_fb2;

    @Column(name = "link_pdf", length = 255)
    private String link_pdf;

    @Column(name = "link_epub", length = 255)
    private String link_epub;

    @Column(name = "link_audio", length = 255)
    private String link_audio;
}

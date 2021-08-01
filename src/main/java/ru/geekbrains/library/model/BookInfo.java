package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "books_info")
public class BookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Book book;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "score")
    private double score;

    @Column(name = "age_recommendation")
    private Integer age_recommendation;
}

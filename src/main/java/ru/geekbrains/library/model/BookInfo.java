package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
    private Book book;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "score")
    private double score;

    @Column(name = "age_recommendation")
    private Integer age_recommendation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInfo bookInfo = (BookInfo) o;
        return id.equals(bookInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

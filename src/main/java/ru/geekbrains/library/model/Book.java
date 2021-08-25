package ru.geekbrains.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@NamedEntityGraph(name = "book-bookInfo-bookStorage-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "bookInfo"),
                @NamedAttributeNode(value = "bookStorage")
        })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "year_of_publish")
    private int yearOfPublish;

    @Column(name = "discount")
    private int discount;

    @Column(name = "editors_advice")
    private boolean editorsAdvice;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private BookInfo bookInfo;

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private BookStorage bookStorage;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Collection<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Collection<Genre> genres;

    public int getCommentsCount() {
        return this.comments.size();
    }

    public void addCommentAndRecalcScore(Comment comment) {
        comment.setBook(this);
        this.comments.add(comment);
        IntSummaryStatistics stats = this.comments.stream().mapToInt(Comment::getScore).summaryStatistics();
        this.bookInfo.setScore(stats.getAverage());

    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}

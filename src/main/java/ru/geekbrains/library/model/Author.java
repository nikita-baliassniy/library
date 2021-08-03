package ru.geekbrains.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "biography")
    private String biography;

    @Column(name = "country")
    private String country;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
//    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.REMOVE})
    @Fetch(FetchMode.SUBSELECT)
//    @JoinTable(name = "books_authors",
//                joinColumns = @JoinColumn(name = "author_id"),
//                inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
}

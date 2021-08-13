package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users_info")
@Data
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "discount")
    private int discount;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate birthday;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

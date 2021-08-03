package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Genre;

import java.net.InterfaceAddress;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Integer deleteGenreById(Long id);
}

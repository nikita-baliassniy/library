package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Author;
import ru.geekbrains.library.model.Book;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    /**
     *
     * @param id Автора
     * @return если > 0 запись удалена успешно, если нет, ошибка удаления (запись не найдена, у автора есть книги)
     */
    Integer deleteAuthorById(Long id);


}

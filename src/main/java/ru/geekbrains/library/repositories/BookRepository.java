package ru.geekbrains.library.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Book;

import javax.persistence.NamedNativeQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    /**
     *
     * @param id книги
     * @return если > 0 запись удалена успешно, если нет, ошибка удаления (запись не найдена)
     */
    Integer deleteBookById(Long id);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value ="book-bookInfo-bookStorage-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value ="book-bookInfo-bookStorage-graph")
    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value ="book-bookInfo-bookStorage-graph")
    Optional<Book> findBookById(Long id);
}

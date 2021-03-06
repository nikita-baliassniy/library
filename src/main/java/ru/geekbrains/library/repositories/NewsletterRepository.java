package ru.geekbrains.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Newsletter;
import ru.geekbrains.library.model.User;

import java.util.List;

@Repository
public interface NewsletterRepository extends CrudRepository<Newsletter, Long> {
    List<Newsletter> findAll();
}

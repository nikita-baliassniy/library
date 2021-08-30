package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Role;
import ru.geekbrains.library.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "user-userInfo-graph")
    Optional<User> findByEmail(String email);

    List<User> findByRolesIn(Collection<Role> roles);
}

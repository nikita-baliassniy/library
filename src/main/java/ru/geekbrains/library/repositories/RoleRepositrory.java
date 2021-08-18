package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepositrory extends JpaRepository<Role, Long> {

    @Query(value="select r from Role r where r.name = 'ROLE_USER'")
    Role getNewUserRole();

}

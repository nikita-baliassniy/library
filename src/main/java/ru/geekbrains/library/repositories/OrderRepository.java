package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Order;
import ru.geekbrains.library.model.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    public List<Order> findAllByOwnerUsername(String ownerUsername);
    public List<Order> findAllByOwnerEmail(String email);

    List<Order> findAllByOwner(User user);
}

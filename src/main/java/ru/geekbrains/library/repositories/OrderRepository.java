package ru.geekbrains.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.library.model.Order;
import ru.geekbrains.library.model.OrderItem;
import ru.geekbrains.library.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOwnerEmail(String email);

    List<Order> findAllByOwner(User user);
}

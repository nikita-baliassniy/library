package ru.geekbrains.library.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.model.Cart;
import ru.geekbrains.library.model.Order;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.repositories.OrderRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createFromUserCart (Cart cart, User user, String address) {
        Order order = new Order(cart, user, address);
        orderRepository.save(order);
        return order;
    }

    public List<Order> findAllOrdersByOwnerName (String username) {
        return orderRepository.findAllByOwnerUsername(username);
    }

    public Optional<Order> getOrderById (Long id) {
        return orderRepository.findById(id);
    }

}

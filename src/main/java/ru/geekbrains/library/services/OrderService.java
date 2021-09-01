package ru.geekbrains.library.services;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.dto.OrderDto;
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
    private final CartService cartService;
    private final ModelMapper modelMapper;

    public OrderDto createFromUserCart (Cart cart, User user) {
        Order newOrder = new Order(cart, user);
        newOrder = orderRepository.save(newOrder);
        cartService.clearCart(cart);
        return modelMapper.map(newOrder, OrderDto.class);
    }

    public List<Order> findAllOrdersByOwnerEmail(String email) {
        return orderRepository.findAllByOwnerEmail(email);
    }

    public Optional<Order> getOrderById (Long id) {
        return orderRepository.findById(id);
    }

}

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
import java.util.stream.Collectors;

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
        cartService.save(cart);
        return modelMapper.map(newOrder, OrderDto.class);
    }

    public List<Order> findAllOrdersByOwnerEmail(String email) {
        List<Order> orders = orderRepository.findAllByOwnerEmail(email);
        return orders;
    }

    public List<OrderDto> findAllByOwner(User owner) {
        List<Order> orders = orderRepository.findAllByOwner(owner);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    public Optional<Order> getOrderById (Long id) {
        return orderRepository.findById(id);
    }

}

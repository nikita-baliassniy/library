package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.OrderDto;
import ru.geekbrains.library.exceptions.CartNotFoundException;
import ru.geekbrains.library.exceptions.OrderNotFoundException;
import ru.geekbrains.library.exceptions.UserNotFoundException;
import ru.geekbrains.library.model.Cart;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.services.CartService;
import ru.geekbrains.library.services.OrderService;
import ru.geekbrains.library.services.UserService;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @PostMapping("/{cartUUID}")
    public OrderDto createOrderFromCart(@PathVariable UUID cartUUID, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
            Cart cart = cartService.findById(cartUUID).orElseThrow(CartNotFoundException::new);
            return orderService.createFromUserCart(cart, user);
        }
        throw new UserNotFoundException();
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById (@PathVariable Long id) {
        return modelMapper.map(orderService.getOrderById(id).orElseThrow(() -> new OrderNotFoundException("Не удалось найти заказ по этому  id: " + id)), OrderDto.class);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders (Principal principal) {
        return orderService.findAllOrdersByOwnerEmail(principal.getName()).stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

}

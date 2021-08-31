package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.OrderDto;
import ru.geekbrains.library.exceptions.OrderNotFoundException;
import ru.geekbrains.library.services.CartService;
import ru.geekbrains.library.services.OrderService;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    //private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;

    // // TODO: 14.08.2021 Пока нету UserService
    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpringDataJaxb.OrderDto createOrderFromCart(Principal principal, @RequestParam UUID cartUuid, @RequestParam String address) {
        return new OrderDto(orderCartUserPolicy.createOrderFromCart(principal.getName(), cartUuid, address));
    }*/

    @GetMapping("/{id}")
    public OrderDto getOrderById (@PathVariable Long id) {
        return new OrderDto(orderService.getOrderById(id).orElseThrow(() -> new OrderNotFoundException("Не удалось найти заказ по этому  id: " + id)));
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders (Principal principal) {
        return orderService.findAllOrdersByOwnerEmail(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }

}

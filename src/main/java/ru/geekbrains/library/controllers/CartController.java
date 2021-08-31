package ru.geekbrains.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.library.dto.CartDto;
import ru.geekbrains.library.exceptions.CartNotFoundException;
import ru.geekbrains.library.model.Cart;
import ru.geekbrains.library.policy.CartBookPolicy;
import ru.geekbrains.library.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartBookPolicy cartBookPolicy;

    @PostMapping
    public UUID createNewCart() {
        Cart cart = cartService.save(new Cart());
        return cart.getId();
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        Cart cart = cartService.findById(uuid).orElseThrow(() -> new CartNotFoundException("Unable to find cart with id: " + uuid));
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public void addBookToCart(@RequestParam UUID uuid, @RequestParam(name = "book_id") Long book_id) {
        cartBookPolicy.addToCart(uuid, book_id);
    }

    @PostMapping("/clear")
    public void clearCart (@RequestParam UUID uuid) {
        cartService.clearCart(uuid);
    }

    @DeleteMapping
    public void deleteBookFromCartById (@RequestParam UUID uuid, @RequestParam(name = "book_id") Long book_id) {
        cartBookPolicy.removeFromCart(uuid, book_id);
    }
}

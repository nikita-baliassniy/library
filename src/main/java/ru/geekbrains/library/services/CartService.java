package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.exceptions.CartNotFoundException;
import ru.geekbrains.library.model.Cart;
import ru.geekbrains.library.repositories.CartRepository;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    @Transactional
    public void clearCart (UUID cartId) {
        Cart cart = findById(cartId).orElseThrow(() -> new CartNotFoundException());
        cart.clear();
    }

    @Transactional
    public void delete(UUID cartId, Long bookId) {
        Cart cart = findById(cartId).orElseThrow(() -> new CartNotFoundException("Корзина не найдена. Во время удаления книги с id: " + bookId + " в корзине"));
        cart.removeFromCartTotally(bookId);
    }
}

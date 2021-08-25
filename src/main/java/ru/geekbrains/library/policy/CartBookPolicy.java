package ru.geekbrains.library.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.exceptions.BookNotFoundException;
import ru.geekbrains.library.exceptions.CartNotFoundException;
import ru.geekbrains.library.model.Book;
import ru.geekbrains.library.model.Cart;
import ru.geekbrains.library.model.CartItem;
import ru.geekbrains.library.services.BookService;
import ru.geekbrains.library.services.CartService;


import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartBookPolicy {
    private final CartService cartService;
    private final BookService bookService;

    @Transactional
    public void addToCart(UUID cartId, Long bookId) {
        Cart cart = cartService.findById(cartId).orElseThrow(() -> new CartNotFoundException("Unable to find cart with id: " + cartId));;
        CartItem cartItem = cart.getItemBookId(bookId);

        if (cartItem != null) {
            cartItem.incrementQuantity();
            cart.recalculate();
            return;
        }
        Book b = bookService.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Unable to add product with id: " + bookId + " to cart. Product doesn't exist"));
        cart.add(new CartItem(b));
    }
}

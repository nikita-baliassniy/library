package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @Column(name = "price")
    private double price;

    public void add(CartItem cartItem) {
        this.items.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    public void recalculate() {
        price = 0;
        for (CartItem ci : items) {
            price += ci.getPrice();
        }
    }

    public void removeFromCartTotally(Long id) {
        ListIterator<CartItem> iterator = items.listIterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getBook().getId().equals(id)) {
                cartItem.setCart(null);
                iterator.remove();
                recalculate();
                break;
            }
        }
    }

    public void clear() {
        items.forEach(i
                -> i.setCart(null));
        items.clear();
        recalculate();
    }

    public CartItem getItemBookId(Long id) {
        for (CartItem ci : items) {
            if (ci.getBook().getId().equals(id)) {
                return ci;
            }
        }
        return null;
    }

}
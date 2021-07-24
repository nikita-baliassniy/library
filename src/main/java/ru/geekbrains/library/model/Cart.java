package ru.geekbrains.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
    private int price;

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

    public void removeFromCart(Long id) {
        ListIterator<CartItem> iterator = items.listIterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getId().equals(id)) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.decrementQuantity();
                } else if (cartItem.getQuantity() == 1) {
                    iterator.remove();
                }
                recalculate();
            }
        }
    }

    public void removeFromCartTotally(Long id) {
        ListIterator<CartItem> iterator = items.listIterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getId().equals(id)) {
                iterator.remove();
                recalculate();
                break;
            }
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }

}
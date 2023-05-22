package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.LineItem;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public class CartServiceImpl {
    private final UserRepository customerRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    public CartServiceImpl(UserRepository customerRepository, ItemRepository itemRepository, CartRepository cartRepository) {
        this.customerRepository=customerRepository;
        this.itemRepository=itemRepository;
        this.cartRepository=cartRepository;
    }
    public Cart getCartByCustomerId(Long customerId){
         Optional<Cart> cart=cartRepository.findById(customerId);
        return cart.orElse(null);
    }

    public void addItemToCart(Long customerId, Long itemId) {
        User customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        Cart cart = getCartByCustomerId(customerId);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }
        //if the cart in not empty...

        LineItem lineItem = new LineItem();
        lineItem.setItem(item);
        lineItem.setCart(cart);
        cart.getLineItems().add(lineItem);
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long customerId, Long itemId) {
        Cart cart = getCartByCustomerId(customerId);
        if (cart != null) {
            LineItem lineItem = cart.getLineItems().stream()
                    .filter(li -> li.getItem().getItemId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Item not found in cart"));

            cart.getLineItems().remove(lineItem);
            cartRepository.save(cart);
        } else {
            throw new EntityNotFoundException("Cart not found");
        }
    }

}

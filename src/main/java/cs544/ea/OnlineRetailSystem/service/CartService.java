package cs544.ea.OnlineRetailSystem.service;
import cs544.ea.OnlineRetailSystem.domain.Cart;

public interface CartService {
    public Cart getCartByCustomerId(Long customerId);

    public void addItemToCart();

    public void removeItemFromCart();

    public void clearCart();

}

package cs544.ea.OnlineRetailSystem.service;
import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;

public interface CartService {
	
    public Cart getCartForCurrentCustomer();
    public void addItemToCart(ItemRequest itemRequest) throws Exception;
    public void removeItemFromCart(Long itemId);
    public void clearCart();
    public OrderResponse checkoutCart();

}
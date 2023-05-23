package cs544.ea.OnlineRetailSystem.service;
import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;

public interface CartService {
	
    public Cart getCartByCustomerId(Long customerId);
    public void addItemToCart(Long customerId, ItemRequest itemRequest) throws Exception;
    public void removeItemFromCart(Long customerId, Long itemId);
    public void clearCart(Long customerId);
    public OrderResponse checkoutCart(Long customerId);

}
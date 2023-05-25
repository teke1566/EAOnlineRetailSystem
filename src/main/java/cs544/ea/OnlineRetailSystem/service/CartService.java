package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.CartResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;

public interface CartService {
	
    CartResponse getCartForCurrentCustomer();
    CartResponse clearCartForCurrentCustomer();
    OrderResponse checkoutCartForCurrentCustomer();
    
    CartResponse addItemToCart(ItemRequest itemRequest) throws Exception;
    CartResponse updateLineItemFromCart(Long lineItemId, ItemRequest itemRequest) throws Exception;
    CartResponse deleteLineItemFromCart(Long lineItemId);

}
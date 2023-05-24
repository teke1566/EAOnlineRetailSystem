package cs544.ea.OnlineRetailSystem.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.LineItem;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.LineItemRepository;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private GetUser getUser;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private ItemRepository itemRepository;
	
	@Autowired
    private CartRepository cartRepository;
	
	@Autowired
	private LineItemRepository lineItemRepository;
    
    
    @Override
    public Cart getCartForCurrentCustomer(){
    	User customer = getUser.getUser();
    	Optional<Cart> cart = cartRepository.findById(customer.getId());
        if (cart.isPresent())
        	return cart.get();
        Cart newCart = new Cart(customer);
        return cartRepository.save(newCart);
    }

    @Override
    public void addItemToCart(ItemRequest itemRequest) throws Exception {
		Cart cart = getCartForCurrentCustomer();
		Item item = itemRepository.findById(itemRequest.getItemId()).orElseThrow(() -> new EntityNotFoundException("Item not found"));
		LineItem lineItem = cart.getLineItems().stream()
								.filter(li -> li.getItem().getItemId().equals(item.getItemId()))
								.findFirst()
								.orElse(new LineItem(item, itemRequest.getDiscount(), cart));
		
		int quantity = itemRequest.getQuantity();
		if (lineItem.getQuantity() > 0) {
			quantity += lineItem.getQuantity();
		}
		lineItem.setQuantity(quantity);

		if (item.getQuantityInStock() < quantity)
			throw new Exception("Item not enough quantity");
	
		cart.addLineItem(lineItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long lineItemId) {
        Cart cart = getCartForCurrentCustomer();
        cart.getLineItems().stream()
        	.filter(li -> li.getLineItemId().equals(lineItemId))
        	.findFirst()
        	.orElseThrow(() -> new EntityNotFoundException("LineItem not found in cart"));
        
        lineItemRepository.deleteLineItemByCartIdAndLineItemId(cart.getId(), lineItemId);
    }

	@Override
	public void clearCart() {
		Cart cart = getCartForCurrentCustomer();
		lineItemRepository.deleteLineItemsByCartId(cart.getId());
	}

	//create a new order
	@Override
	public OrderResponse checkoutCart() {
		User customer = getUser.getUser();
		Cart cart = getCartForCurrentCustomer();
		
		List<LineItem> lineItems = new ArrayList<>();
		cart.getLineItems().forEach(li -> lineItems.add(li));
		lineItemRepository.deleteLineItemsByCartId(cart.getId()); //clear cart
		
		Order order = new Order(customer);
		order.setLineItems(lineItems);
		return mapper.map(orderRepository.save(order), OrderResponse.class);
	}
	
}

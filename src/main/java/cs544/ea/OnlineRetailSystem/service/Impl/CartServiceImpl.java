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
import cs544.ea.OnlineRetailSystem.domain.dto.response.CartResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
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
    private CartRepository cartRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private ItemRepository itemRepository;

    
    private Cart getCartForCurrentCustomerHelper() {
    	User customer = getUser.getUser();
    	Cart cart = cartRepository.findCartByCustomerId(customer.getId());
        if (cart == null)
        	cart = new Cart(customer);
        return cartRepository.save(cart);
    }
	
    @Override
    public CartResponse getCartForCurrentCustomer() {
    	return mapper.map(getCartForCurrentCustomerHelper(), CartResponse.class);
    }
    
    @Override
    public CartResponse addItemToCart(ItemRequest itemRequest) throws Exception {
    	Cart cart = getCartForCurrentCustomerHelper();

		Item item = itemRepository.findById(itemRequest.getItemId())
				.orElseThrow(() -> new EntityNotFoundException("Item not found"));
		
		Optional<LineItem> lineItem = cart.getLineItems().stream()
								.filter(li -> li.getItem().getItemId().equals(item.getItemId()))
								.findFirst();
		
		if (lineItem.isPresent()) {
			LineItem li = lineItem.get();
			int quantity = li.getQuantity() + itemRequest.getQuantity();
			if (item.getQuantityInStock() < quantity)
				throw new Exception("Item not enough quantity");
			li.setQuantity(quantity);
		} else {
			if (item.getQuantityInStock() < itemRequest.getQuantity())
				throw new Exception("Item not enough quantity");
			LineItem li = new LineItem(item, itemRequest.getQuantity(), itemRequest.getDiscount());
			cart.addLineItem(li);
		}
		
        return mapper.map(cartRepository.save(cart), CartResponse.class);
    }

    @Override
    public CartResponse deleteLineItemFromCart(Long lineItemId) {
    	Cart cart = getCartForCurrentCustomerHelper();
    	cart.removeLineItem(lineItemId);
        return mapper.map(cartRepository.save(cart), CartResponse.class);
    }
    
	@Override
	public CartResponse updateLineItemFromCart(Long lineItemId, ItemRequest itemRequest) throws Exception {
		Cart cart = getCartForCurrentCustomerHelper();
		LineItem lineItem = cart.getLineItems().stream()
				.filter(li -> li.getLineItemId().equals(lineItemId) && li.getItem().getItemId().equals(itemRequest.getItemId()))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("LineItem does not exist"));
		
		Item item = itemRepository.findById(itemRequest.getItemId())
				.orElseThrow(() -> new EntityNotFoundException("Item not found"));
		
		int quantity = itemRequest.getQuantity();
		
		if (item.getQuantityInStock() < quantity)
			throw new Exception("Item not enough quantity");
		
		lineItem.setQuantity(quantity);
		lineItem.setDiscount(itemRequest.getDiscount());
		return mapper.map(cartRepository.save(cart), CartResponse.class);
	}

	@Override
	public CartResponse clearCartForCurrentCustomer() {
		Cart cart = getCartForCurrentCustomerHelper();
		cart.clearCart();
		return mapper.map(cartRepository.save(cart), CartResponse.class);
	}

	//create a new order
	@Override
	public OrderResponse checkoutCartForCurrentCustomer() {
		Cart cart = getCartForCurrentCustomerHelper();
		Order order = new Order(cart.getCustomer());
		List<LineItem> lineItems = new ArrayList<>();
		cart.getLineItems().forEach(li -> {
			lineItems.add(new LineItem(li.getItem(), li.getQuantity(), li.getDiscount()));
		});
		order.setLineItems(lineItems);
		clearCartForCurrentCustomer();
		return mapper.map(orderRepository.save(order), OrderResponse.class);
	}

}

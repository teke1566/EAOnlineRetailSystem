package cs544.ea.OnlineRetailSystem.service.Impl;

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
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ModelMapper mapper;
	
	private final OrderRepository orderRepository;
    private final UserRepository customerRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    
    public CartServiceImpl(OrderRepository orderRepository, UserRepository customerRepository, ItemRepository itemRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
    	this.customerRepository=customerRepository;
        this.itemRepository=itemRepository;
        this.cartRepository=cartRepository;
    }
    
    public Cart getCartByCustomerId(Long customerId){
    	Optional<Cart> cart = cartRepository.findById(customerId);
        if (cart.isPresent())
        	return cart.get();
        Optional<User> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
        	Cart newCart = new Cart(customer.get());
        	return cartRepository.save(newCart);
        }
        throw new EntityNotFoundException("Customer not found");
    }

    public void addItemToCart(Long customerId, ItemRequest itemRequest) throws Exception {
    	try {
    		Cart cart = getCartByCustomerId(customerId);
    		Item item = itemRepository.findById(itemRequest.getItemId()).orElseThrow(() -> new EntityNotFoundException("Item not found"));
    		if (item.getQuantityInStock() < itemRequest.getQuantity())
    			throw new Exception("Item not enough quantity");
    		LineItem lineItem = new LineItem(item, itemRequest.getQuantity(), itemRequest.getDiscount(), cart);
            cart.addLineItem(lineItem);
            cartRepository.save(cart);
    	} catch (EntityNotFoundException e) {
    		throw e;
    	}
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

	@Override
	public void clearCart(Long customerId) {
		try {
			Cart cart = getCartByCustomerId(customerId);
			cart.clearCart();
			cartRepository.save(cart);
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}

	//create a new order
	@Override
	public OrderResponse checkoutCart(Long customerId) {
		try {
			Cart cart = getCartByCustomerId(customerId);
			User customer = customerRepository.findById(customerId).get();
			Order order = new Order(customer);
			order.setLineItems(cart.getLineItems());
			cart.clearCart();
			cartRepository.save(cart);
			return mapper.map(orderRepository.save(order), OrderResponse.class);
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}
	
}

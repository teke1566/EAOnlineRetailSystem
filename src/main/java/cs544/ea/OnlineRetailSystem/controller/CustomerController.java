package cs544.ea.OnlineRetailSystem.controller;

import java.util.List;

import cs544.ea.OnlineRetailSystem.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.service.CartService;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import cs544.ea.OnlineRetailSystem.util.CustomErrorType;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
    private final CustomerService customerService;
    private final OrderService orderService;
    private final PublicService publicService;
    private final CartService cartService;
    
//    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService
            , PublicService publicService, CartService cartService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.publicService = publicService;
        this.cartService = cartService;
    }

    //localhost:9098/api/v1/customers/
//    @GetMapping("/") //working
//    public List<User> getAllCustomers(){
//       return  customerService.getAllCustomers();
//    }
    //localhost:9098/api/v1/customers/300
//    @GetMapping("/{customerId}")// working
//    public User getCustomerById(@PathVariable Long customerId){
//        return customerService.getCustomerById(customerId);
//    }
//    //localhost:9098/api/v1/customers/
//    @PostMapping// working
//    public User addNewCustomer(@RequestBody User user){
//       return customerService.addCustomer(user);
//    }
    @PutMapping("/{customerId}")//working
    public User updateCustomer(@PathVariable Long customerId,@RequestBody User user){
        return customerService.updateCustomer(customerId,user);
    }
//    @DeleteMapping("/{customerId}")//working
//    public void deleteCustomer(@PathVariable Long customerId) throws Exception {
//        customerService.deleteCustomerById(customerId);
//    }


    //CreditCard:
//    @GetMapping("/credit-cards")// working....get all credit card
//    public List<CreditCard> getAllCreditCards(){
//        return customerService.getAllCreditCards();
//    }
//    @GetMapping("/credit-cards/{creditCardId}")//working .. getCreditCardById
//    public CreditCard getCreditCardById(@PathVariable Long creditCardId){
//        return customerService.getCreditCardById(creditCardId);
//    }

    @PostMapping("/credit-cards")
    public CreditCard addNewCreditCard(@RequestBody CreditCard creditCard){
        return publicService.addCreditCard(creditCard);
    }
    @PutMapping("/credit-cards/{creditCardId}")//working
    public CreditCard updateCreditCard(@PathVariable Long creditCardId, @RequestBody CreditCard creditCard){
        return customerService.updateCreditCard(creditCardId,creditCard);
    }
    @DeleteMapping("/credit-cards/{creditCardId}")// working
    public void deleteCreditCardById(@PathVariable Long creditCardId){
        customerService.deleteCreditCardById(creditCardId);
    }
    
    // Shipping Address:
    @PostMapping("/shippingAddress")
    public Address addShippingAddress(@RequestBody Address address) {
        return publicService.addShippingAddress(address);
    }

    @PutMapping("/shippingAddress/{shippingAddressId}")
    public Address updateShippingAddress(@PathVariable Long shippingAddressId, @RequestBody Address address) {
        return customerService.updateShippingAddress(shippingAddressId, address);
    }

    @DeleteMapping("/shippingAddress/{shippingAddressId}")
    public void deleteShippingAddress(@PathVariable Long shippingAddressId) {
        customerService.deleteShippingAddressById(shippingAddressId);
    }

//    @GetMapping("/shippingAddresses")
//    public List<Address> getAllShippingAddresses() {
//        return customerService.getAllShippingAddress();
//    }

    // Billing Address:

    @PostMapping("/billingAddress")
    public Address addBillingAddress(@RequestBody Address address) {
        return publicService.addBillingAddress(address);
    }

    @PutMapping("/billingAddress/{billingAddressId}")
    public Address updateBillingAddress(@PathVariable Long billingAddressId, @RequestBody Address address) {
        return customerService.updateBillingAddress(billingAddressId, address);
    }

    @DeleteMapping("/billingAddress/{billingAddressId}")
    public void deleteBillingAddress(@PathVariable Long billingAddressId) {
        customerService.deleteBillingAddressById(billingAddressId);
    }

//    @GetMapping("/billingAddresses")
//    public List<Address> getAllBillingAddresses() {
//        return customerService.getAllBillingAddress();
//    }
//
    
    /** CART APIs */
    
    // GET /api/v1/customers/cart
    // get cart for the current customer
    @GetMapping("/cart")
    public ResponseEntity<?> getCustomerCart() {
    	return new ResponseEntity<Cart>(cartService.getCartForCurrentCustomer(), HttpStatus.OK);
    }
    
    // POST /api/v1/customers/cart
    // checkout cart for the current customer
    @PostMapping("/cart")
    public ResponseEntity<?> checkoutCart() {
    	try {
    		return new ResponseEntity<OrderResponse>(cartService.checkoutCart(), HttpStatus.OK);
    	} catch(EntityNotFoundException e) {
    		return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
    	}
    }
    
    // POST /api/v1/customers/cart/items
    // add Item to Cart
    @PostMapping("/cart/items")
    public ResponseEntity<?> addItemToCart(@RequestBody ItemRequest itemRequest) {
    	try {
    		cartService.addItemToCart(itemRequest);
    		return new ResponseEntity<>(HttpStatus.OK);
    	} catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
    	}
    }
    
    // DELETE /api/v1/customers/cart/items/:lineItemId
    // given lineItemId remove Item from Cart
    @DeleteMapping("/cart/items/{lineItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long lineItemId) {
    	try {
    		cartService.removeItemFromCart(lineItemId);
    		return new ResponseEntity<>(HttpStatus.OK);
    	} catch(EntityNotFoundException e) {
    		return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
    	}
    }
    
    // DELETE /api/v1/customers/cart
    // clear the current customer cart
    @DeleteMapping("/cart")
    public ResponseEntity<?> clearCart() {
		cartService.clearCart();
		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    //Orders
    
    // GET /api/v1/customers/orders?orderStatus=PLACED
    // GET /api/v1/customers/orders
    @GetMapping("/{customerId}/orders")
    public List<OrderResponse> getCustomerAllOrders(@PathVariable Long customerId, @RequestParam OrderStatus orderStatus) {
    	if (orderStatus != null)
    		return orderService.getCustomerOrderByStatus(customerId, orderStatus);
    	return orderService.getCustomerAllOrders(customerId);
    }
    
    @GetMapping("/{customerId}/orders/{orderId}") //
    public ResponseEntity<?> getCustomerOrderById(@PathVariable Long customerId, @PathVariable Long orderId) {
    	try {
    		return new ResponseEntity<OrderResponse>(orderService.getCustomerOrderById(customerId, orderId), HttpStatus.OK);
    	} catch (EntityNotFoundException e) {
    		return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
    	}
    }
    	
//	@PutMapping("/{customerId}/orders/{orderId}")
//	public ResponseEntity<?> updateOrderById(@PathVariable Long customerId, @PathVariable Long orderId, @RequestBody Order order) {
//		try {
//			return new ResponseEntity<OrderResponse>(orderService.updateCustomerOrderById(customerId, orderId, order), HttpStatus.OK);
//		} catch (EntityNotFoundException e) {
//			return new ResponseEntity<>(new CustomErrorType("Order not found"), HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new CustomErrorType("Cannot delete order"), HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@DeleteMapping("/{customerId}/orders/{orderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable Long customerId, @PathVariable Long orderId) {
		try {
			orderService.deleteCustomerOrderById(customerId, orderId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(new CustomErrorType("Order not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Cannot delete order"), HttpStatus.BAD_REQUEST);
		}
	}
}

package cs544.ea.OnlineRetailSystem.controller;

import java.util.List;

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
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import cs544.ea.OnlineRetailSystem.util.CustomErrorType;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    public CustomerController(CustomerService customerService, OrderService orderService){
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<User> getAllCustomers(){
       return  customerService.getAllCustomers();
    }
    @GetMapping("/{customerId}")
    public User getCustomerById(@PathVariable Long customerId){
        return customerService.getCustomerById(customerId);
    }
    @PostMapping
    public User addNewCustomer(@RequestBody User user){
       return customerService.addCustomer(user);
    }
    @PutMapping("/customers/{customerId}")
    public User updateCustomer(@PathVariable Long customerId,@RequestBody User user){
        return customerService.updateCustomer(customerId,user);
    }
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        //System.out.println("here");
        customerService.deleteCustomerById(customerId);
    }


    //CreditCard:
    @GetMapping("/credit-cards")
    public List<CreditCard> getAllCreditCards(){
        return customerService.getAllCreditCards();
    }
    @GetMapping("/credit-cards/{creditCardId}")
    public CreditCard getCreditCardById(@PathVariable Long creditCardId){
        return customerService.getCreditCardById(creditCardId);
    }

    @PostMapping("/credit-cards")
    public CreditCard addNewCreditCard(@RequestBody CreditCard creditCard){
        return customerService.addCreditCard(creditCard);
    }
    @PutMapping("/credit-cards/{creditCardId}")
    public CreditCard updateCreditCard(@PathVariable Long creditCardId, @RequestBody CreditCard creditCard){
        return customerService.updateCreditCard(creditCardId,creditCard);
    }
    @DeleteMapping("/credit-cards/{creditCardId}")
    public void deleteCreditCardById(@PathVariable Long creditCardId){
        customerService.deleteCreditCardById(creditCardId);
    }
    
    // Shipping Address:
    @PostMapping("/shippingAddress")
    public Address addShippingAddress(@RequestBody Address address) {
        return customerService.addShippingAddress(address);
    }

    @PutMapping("/shippingAddress/{shippingAddressId}")
    public Address updateShippingAddress(@PathVariable Long shippingAddressId, @RequestBody Address address) {
        return customerService.updateShippingAddress(shippingAddressId, address);
    }

    @DeleteMapping("/shippingAddress/{shippingAddressId}")
    public void deleteShippingAddress(@PathVariable Long shippingAddressId) {
        customerService.deleteShippingAddressById(shippingAddressId);
    }

    @GetMapping("/shippingAddresses")
    public List<Address> getAllShippingAddresses() {
        return customerService.getAllShippingAddress();
    }

    // Billing Address:

    @PostMapping("/billingAddress")
    public Address addBillingAddress(@RequestBody Address address) {
        return customerService.addBillingAddress(address);
    }

    @PutMapping("/billingAddress/{billingAddressId}")
    public Address updateBillingAddress(@PathVariable Long billingAddressId, @RequestBody Address address) {
        return customerService.updateBillingAddress(billingAddressId, address);
    }

    @DeleteMapping("/billingAddress/{billingAddressId}")
    public void deleteBillingAddress(@PathVariable Long billingAddressId) {
        customerService.deleteBillingAddressById(billingAddressId);
    }

    @GetMapping("/billingAddresses")
    public List<Address> getAllBillingAddresses() {
        return customerService.getAllBillingAddress();
    }
    
    //Orders
    @GetMapping("/{customerId}/orders")
    public List<OrderResponse> getCustomerAllOrders(@PathVariable Long customerId, @RequestParam OrderStatus orderStatus) {
    	if (orderStatus != null)
    		return orderService.getCustomerOrderByStatus(customerId, orderStatus);
    	return orderService.getCustomerAllOrders(customerId);
    }
    
    @GetMapping("/{customerId}/orders/{orderId}")
    public ResponseEntity<?> getCustomerOrderById(@PathVariable Long customerId, @PathVariable Long orderId) {
    	try {
    		return new ResponseEntity<OrderResponse>(orderService.getCustomerOrderById(customerId, orderId), HttpStatus.OK);
    	} catch (EntityNotFoundException e) {
    		return new ResponseEntity<>(new CustomErrorType("Order not found"), HttpStatus.NOT_FOUND);
    	}
    }
    	
	@PutMapping("/{customerId}/orders/{orderId}")
	public ResponseEntity<?> updateOrderById(@PathVariable Long customerId, @PathVariable Long orderId, @RequestBody Order order) {
		try {
			return new ResponseEntity<OrderResponse>(orderService.updateCustomerOrderById(customerId, orderId, order), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(new CustomErrorType("Order not found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Cannot delete order"), HttpStatus.BAD_REQUEST);
		}
	}
	
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

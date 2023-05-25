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
import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Review;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.CartResponse;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.service.CartService;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.service.OrderService;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import cs544.ea.OnlineRetailSystem.util.CustomErrorType;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final PublicService publicService;
    private final CartService cartService;
    private final ItemService itemService;

    public CustomerController(CustomerService customerService,
                              OrderService orderService,
                              PublicService publicService,
                              CartService cartService,
                              ItemService itemService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.publicService = publicService;
        this.cartService = cartService;
        this.itemService=itemService;
    }


    @GetMapping("/items")
    public List<Item> getAllItems() {
        return publicService.getAllItems();
    }

    @GetMapping("/items/{itemId}")

    public Item getItemById(@PathVariable Long itemId) {
        return publicService.getItemById(itemId);
    }

    @PutMapping("/{customerId}")//working
    public User updateCustomer(@PathVariable Long customerId, @RequestBody User user) {
        return customerService.updateCustomer(customerId, user);
    }

    /**
     * ORDER APIs
     */

    // GET /api/v1/customers/orders?orderStatus=PLACED
    // get all orders for the current customer; can use orderStatus as a filter
    @GetMapping("/orders")
    public ResponseEntity<?> getCustomerAllOrders(@RequestParam String orderStatus) {
    	return new ResponseEntity<List<OrderResponse>>(orderService.getCustomerAllOrders(orderStatus), HttpStatus.OK);
    }


    // GET /api/v1/customers/orders/:orderId
    // get the current customer order by orderId
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getCustomerOrderById(@PathVariable Long orderId) {
        try {
            return new ResponseEntity<OrderResponse>(orderService.getCustomerOrderById(orderId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/v1/customers/orders/:itemId
    // get the current customer order by itemId
//    @GetMapping("/orders/{itemId}")
//    public ResponseEntity<?> getCustomerOrderByItemId(@PathVariable Long itemId) {
//        try {
//            return new ResponseEntity<List<Order>>(publicService.getOrderByItemId(itemId), HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.OK);
//        }
//    }

    // POST /api/v1/customers/orders/:orderId
    // place a new order
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long orderId) {
        try {
            return new ResponseEntity<OrderResponse>(orderService.placeOrder(orderId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * CART APIs
     */

    // GET /api/v1/customers/cart
    // get cart for the current customer
    @GetMapping("/cart")
    public ResponseEntity<?> getCustomerCart() {
        return new ResponseEntity<CartResponse>(cartService.getCartForCurrentCustomer(), HttpStatus.OK);
    }

    // POST /api/v1/customers/cart
    // checkout cart for the current customer
    @PostMapping("/cart")
    public ResponseEntity<?> checkoutCartForCurrentCustomer() {
        try {
            return new ResponseEntity<OrderResponse>(cartService.checkoutCartForCurrentCustomer(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    // DELETE /api/v1/customers/cart
    // clear the current customer cart
    @DeleteMapping("/cart")
    public ResponseEntity<?> clearCartForCurrentCustomer() {
    	return new ResponseEntity<CartResponse>(cartService.clearCartForCurrentCustomer(), HttpStatus.OK);
    }

    // POST /api/v1/customers/cart/items
    // add Item to Cart
    @PostMapping("/cart/items")
    public ResponseEntity<?> addItemToCart(@RequestBody ItemRequest itemRequest) {
        try {
            return new ResponseEntity<CartResponse>(cartService.addItemToCart(itemRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // PUT /api/v1/customers/cart/items/:lineItemId
    // update lineItem of the current customer cart
    @PutMapping("/cart/lineItems/{lineItemId}")
    public ResponseEntity<?> updateLineItemFromCart(@PathVariable Long lineItemId, @RequestBody ItemRequest itemRequest) {
    	try {
    		return new ResponseEntity<CartResponse>(cartService.updateLineItemFromCart(lineItemId, itemRequest), HttpStatus.OK);
    	} catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    }
    
    // DELETE /api/v1/customers/cart/items/:lineItemId
    // given lineItemId remove Item from Cart
    @DeleteMapping("/cart/lineItems/{lineItemId}")
    public ResponseEntity<?> deleteLineItemFromCart(@PathVariable Long lineItemId) {
        try {
            return new ResponseEntity<CartResponse>(cartService.deleteLineItemFromCart(lineItemId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    //Review API

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviews() {
        try {
            return new ResponseEntity<>(itemService.getAllReviewByCustomerId(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()),HttpStatus.OK);
        }
    }
    @PostMapping("/reviews/{itemId}")
    public ResponseEntity<?> addReview(@PathVariable Long itemId,@RequestBody Review review){
        try {
            return new ResponseEntity<>(itemService.addReview(itemId,review),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()),HttpStatus.NO_CONTENT);
        }
    }



    //CreditCard End Point

    @GetMapping("/credit-cards")
    public ResponseEntity<?> getAllCreditCardsWithCustomerId(){
        try {
            return new ResponseEntity<>(publicService.getCreditCardByUserId(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/credit-cards/{creditCardId}")//working .. getCreditCardById
    public CreditCard getCreditCardByCreditCardId(@PathVariable Long creditCardId) {
        return customerService.getCreditCardById(creditCardId);
    }

    @PostMapping("/credit-cards")
    public List<CreditCard> addNewCreditCard(@RequestBody CreditCard creditCard) {
        return publicService.addCreditCard(creditCard);
    }
    @PutMapping("/credit-cards/{creditCardId}")//working
    public CreditCard updateCreditCard(@PathVariable Long creditCardId, @RequestBody CreditCard creditCard) {
        return publicService.updateCreditCardById(creditCardId, creditCard);
    }

    @DeleteMapping("/credit-cards/{creditCardId}")// working
    public void deleteCreditCardById(@PathVariable Long creditCardId) {
        publicService.deleteCreditCardById(creditCardId);
    }



    //Address API

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


    //    @DeleteMapping("/{customerId}")//working
//    public void deleteCustomer(@PathVariable Long customerId) throws Exception {
//        customerService.deleteCustomerById(customerId);
//    }


    //CreditCard:
//    @GetMapping("/credit-cards")// working....get all credit card
//    public List<CreditCard> getAllCreditCards(){
//        return customerService.getAllCreditCards();
//    }


}

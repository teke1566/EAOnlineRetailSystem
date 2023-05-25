package cs544.ea.OnlineRetailSystem.controller;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.domain.dto.request.ItemRequest;
import cs544.ea.OnlineRetailSystem.domain.dto.response.OrderResponse;
import cs544.ea.OnlineRetailSystem.service.*;
import cs544.ea.OnlineRetailSystem.util.CustomErrorType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET /api/v1/customers/orders
    // GET /api/v1/customers/orders?orderStatus=PLACED
    // get all orders for the current customer; can use orderStatus as a filter
    @GetMapping("/orders")
    public List<OrderResponse> getCustomerAllOrders(@RequestParam String orderStatus) {
        if (!orderStatus.isEmpty())
            return orderService.getCustomerOrderByStatus(orderStatus);
        return orderService.getCustomerAllOrders();
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

    @GetMapping("/orders/{itemId}")
    public ResponseEntity<?> getCustomerOrderByItemId(@PathVariable Long itemId) {
        try {
            return new ResponseEntity<List<Order>>(publicService.getOrderByItemId(itemId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.OK);
        }
    }

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
        return new ResponseEntity<Cart>(cartService.getCartForCurrentCustomer(), HttpStatus.OK);
    }

    // POST /api/v1/customers/cart
    // checkout cart for the current customer
    @PostMapping("/cart")
    public ResponseEntity<?> checkoutCart() {
        try {
            return new ResponseEntity<OrderResponse>(cartService.checkoutCart(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
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
        } catch (Exception e) {
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
        } catch (EntityNotFoundException e) {
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

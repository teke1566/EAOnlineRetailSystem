package cs544.ea.OnlineRetailSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cs544.ea.OnlineRetailSystem.DTO.OrderResponse;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.OrderService;

@RestController
@RequestMapping("/api/v1/customer")
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
    @PostMapping("/add-new-customer")
    public User addNewCustomer(@RequestBody User user){
       return customerService.addCustomer(user);
    }
    @PutMapping("/update-customer/{customerId}")
    public User updateCustomer(@PathVariable Long customerId,@RequestBody User user){
        return customerService.updateCustomer(customerId,user);
    }
    @DeleteMapping("/delete-customer/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomerById(customerId);
    }


    //CreditCard:
    @GetMapping("/creditCards")
    public List<CreditCard> getAllCreditCards(){
        return customerService.getAllCreditCards();
    }
    @GetMapping("/creditCard/{creditCardId}")
    public CreditCard getCreditCardById(@PathVariable Long creditCardId){
        return customerService.getCreditCardById(creditCardId);
    }

    @PostMapping("/creditCard/add-new-creditCard")
    public CreditCard addNewCreditCard(@RequestBody CreditCard creditCard){
        return customerService.addCreditCard(creditCard);
    }
    @PutMapping("/creditCard/update-creditCard/{creditCardId}")
    public CreditCard updateCreditCard(@PathVariable Long creditCardId, @RequestBody CreditCard creditCard){
        return customerService.updateCreditCard(creditCardId,creditCard);
    }
    @DeleteMapping("/creditCard/delete-credit-card/{creditCardId}")
    public void deleteCreditCardById(@PathVariable Long creditCardId){
        customerService.deleteCreditCardById(creditCardId);
    }

    //Orders
    @GetMapping("/{customerId}/orders")
    public List<OrderResponse> getOrdersByCustomerId(@PathVariable Long customerId) {
    	return orderService.getOrdersByUserId(customerId);
    }
}

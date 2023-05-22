package cs544.ea.OnlineRetailSystem.controller;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
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
    // Shipping Address:

    @PostMapping("/shippingAddress/add-new-address")
    public Address addShippingAddress(@RequestBody Address address) {
        return customerService.addShippingAddress(address);
    }

    @PutMapping("/shippingAddress/update-address/{shippingAddressId}")
    public Address updateShippingAddress(@PathVariable Long shippingAddressId, @RequestBody Address address) {
        return customerService.updateShippingAddress(shippingAddressId, address);
    }

    @DeleteMapping("/shippingAddress/delete-address/{shippingAddressId}")
    public void deleteShippingAddress(@PathVariable Long shippingAddressId) {
        customerService.deleteShippingAddressById(shippingAddressId);
    }

    @GetMapping("/shippingAddresses")
    public List<Address> getAllShippingAddresses() {
        return customerService.getAllShippingAddress();
    }

    // Billing Address:

    @PostMapping("/billingAddress/add-new-address")
    public Address addBillingAddress(@RequestBody Address address) {
        return customerService.addBillingAddress(address);
    }

    @PutMapping("/billingAddress/update-address/{billingAddressId}")
    public Address updateBillingAddress(@PathVariable Long billingAddressId, @RequestBody Address address) {
        return customerService.updateBillingAddress(billingAddressId, address);
    }

    @DeleteMapping("/billingAddress/delete-address/{billingAddressId}")
    public void deleteBillingAddress(@PathVariable Long billingAddressId) {
        customerService.deleteBillingAddressById(billingAddressId);
    }

    @GetMapping("/billingAddresses")
    public List<Address> getAllBillingAddresses() {
        return customerService.getAllBillingAddress();
    }
}

package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;

import java.util.List;

public interface CustomerService {

    //User...Customer
    public User addCustomer(User user);

    public User getCustomerById(Long customerId);

    public List<User> getAllCustomers();

    public void deleteCustomerById(Long customerId) throws Exception;

    public User updateCustomer(Long customerId, User user);

    public Address getCustomerDefaultShippingAddress(Long customerId);

    //CreditCard

//    public CreditCard addCreditCard(CreditCard creditCard);
    public CreditCard getCreditCardById(Long creditCardId);


    public List<CreditCard> getAllCreditCards();

    //ShippingAddress

//    public Address addShippingAddress(Address address);

    public Address updateShippingAddress(Long shippingAddressId, Address address);

    public void deleteShippingAddressById(Long shippingAddressId);

    public List<Address> getAllShippingAddress();

    //BillingAddress

//    public Address addBillingAddress(Address address);

    public void deleteBillingAddressById(Long billingAddressId);

    public Address updateBillingAddress(Long billingAddressId, Address address);
    public  List<Address> getAllBillingAddress();
    //public Boolean isCurrentUser();




}

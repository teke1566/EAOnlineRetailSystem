package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;

import java.util.List;

public interface CustomerService {

    //User...Customer
    public User addCustomer(User user);

    public User getCustomerById(Long customerId);

    public List<User> getAllCustomer();

    public void deleteCustomerById(Long customerId);

    public User updateCustomer(Long customerId, User user);

    //CreditCard

    public CreditCard addCreditCard(CreditCard creditCard);

    public void deleteCreditCardById(Long creditCardId);

    public CreditCard updateCreditCard(Long creditCardId, CreditCard creditCard);

    public List<CreditCard> getAllCreditCard();

    //ShippingAddress

    public Address addShippingAddress(Address address);

    public Address updateShippingAddress(Long shippingAddressId, Address address);

    public void deleteShippingAddressById(Long shippingAddressId);

    public List<Address> getAllShippingAddress();

    //BillingAddress

    public Address addBillingAddress(Address address);

    public void deleteBillingAddressById(Long billingAddressId);

    public Address updateBillingAddress(Long billingAddressId, Address address);
    public  List<Address> getAllBillingAddress();


}

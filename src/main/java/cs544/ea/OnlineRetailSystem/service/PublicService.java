package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.*;

import java.util.List;

public interface PublicService {

    public List<Item> getAllItems(); // will be accessed by Admin and Customer
    public void deleteItemById(Long itemId);// will be accessed by Admin and Merchant(we have to check whether the item is belong to the merchant or not)

    public Item getItemById(Long itemId);// will be accessed by customer and Merchant(will find item by item id and will check if the item belong to merchant)
    public Address addShippingAddress(Address address); //will be accessed by customer and merchant
    public Address addBillingAddress(Address address); //will be accessed by customer and merchant

    public CreditCard addCreditCard(CreditCard creditCard); // will be accessed by customer and merchant
    public User updateCustomer(User user);// will be accessed by customer and admin
    public void deleteCreditCard(Long creditCardId);// will be accessed by customer and merchant
    public void deleteBillingAddressById(Long billingAddressId);//will be accessed by customer and merchant
    public Address updateBillingAddress(Long billingAddressId, Address address);//will be accessed by customer and merchant
    public Address updateShippingAddress(Long shippingAddressId, Address address);//will be accessed by customer and merchant

    public List<Order> getAllOrderByUserId();// will be accessed by customer and merchant
    public List<Order> getOrderByItemId(Long itemId); //will be accessed by customer and merchant
}

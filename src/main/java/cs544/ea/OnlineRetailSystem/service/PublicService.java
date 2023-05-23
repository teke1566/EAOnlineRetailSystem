package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.*;

import java.util.List;

public interface PublicService {

    public List<Item> getAllItem(); // will be accessed by Admin and Customer
    public void deleteItemById(Long itemId);// firs we have to check if the item is belonged to the merchant or not

    public Item getItemById(Long itemId);// will be accessed by customer and Merchant(will find item by item id and will check if the item belong to merchant)
    public Address addShippingAddress(Address address); //will be accessed by customer and merchant
    public Address addBillingAddress(Address address); //will be accessed by customer and merchant

    public CreditCard addCreditCard(CreditCard creditCard); // will be accessed by customer and merchant
    public User updateCustomer();// will be accessed by customer and admin
    public void deleteCreditCard();// will be accessed by customer and merchant
    public void deleteBillingAddress();//will be accessed by customer and merchant
    public Address updateBillingAddress();//will be accessed by customer and merchant
    public Address updateShippingAddress();//will be accessed by customer and merchant

    public List<Order> getAllOrderById();// will be accessed by customer and merchant
    public Order getOrderByItemId(); //will be accessed by customer and merchant
}

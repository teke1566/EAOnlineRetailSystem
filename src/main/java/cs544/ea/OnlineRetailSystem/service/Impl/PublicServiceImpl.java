package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.service.PublicService;

import java.util.List;

public class PublicServiceImpl implements PublicService {
    @Override
    public List<Item> getAllItem() {
        return null;
    }

    @Override
    public Item getItemById(Long itemId) {
        return null;
    }

    @Override
    public Address addShippingAddress(Address address) {
        return null;
    }

    @Override
    public Address addBillingAddress(Address address) {
        return null;
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard) {
        return null;
    }

    @Override
    public User updateCustomer() {
        return null;
    }

    @Override
    public void deleteCreditCard() {

    }

    @Override
    public void deleteBillingAddress() {

    }

    @Override
    public Address updateBillingAddress() {
        return null;
    }

    @Override
    public Address updateShippingAddress() {
        return null;
    }

    @Override
    public List<Order> getAllOrderById() {
        return null;
    }

    @Override
    public Order getOrderByItemId() {
        return null;
    }
}
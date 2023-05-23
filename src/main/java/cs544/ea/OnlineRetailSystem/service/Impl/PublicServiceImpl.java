package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.repository.CreditCardRepository;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository customerRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public void deleteItemById(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);

    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(()-> new IllegalArgumentException("Item Not Found"));
    }

    @Override
    public Address addShippingAddress(Address address) {
        User user = getUser();
        address.setAddressType(AddressType.SHIPPING);
        user.getShippingAddresses().add(address);

        return customerRepository.save(user).getShippingAddresses()
                .stream()
                .filter(a -> a.equals(address))
                .findFirst()
                .orElse(null);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return customerRepository.findByname(username);
        }

        return null;
    }

    @Override
    public Address addBillingAddress(Address address) {
        User user = getUser();
        address.setAddressType(AddressType.BILLING);
        user.setBillingAddress(address);
        return customerRepository.save(user).getBillingAddress();
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
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
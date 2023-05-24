package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.*;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicServiceImpl implements PublicService {
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private ItemRepository itemRepository;

    private final GetUser getUser;
@Autowired
    public PublicServiceImpl(UserRepository userRepository, CreditCardRepository creditCardRepository, AddressRepository addressRepository, OrderRepository orderRepository, GetUser getUser, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.getUser = getUser;
        this.itemRepository = itemRepository;
    }



    public GetUser getAuthenticatedUser() {
        return getUser;
    }

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
        User user = getUser.getUser();
        address.setAddressType(AddressType.SHIPPING);
        user.getShippingAddresses().add(address);
        return userRepository.save(user).getShippingAddresses()
                .stream()
                .filter(a -> a.equals(address))
                .findFirst()
                .orElse(null);
    }



    @Override
    public Address addBillingAddress(Address address) {
        User user = getUser.getUser();
        address.setAddressType(AddressType.BILLING);
        user.setBillingAddress(address);
        return userRepository.save(user).getBillingAddress();
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public User updateCustomer(User user) {
        User existingUser = getUser.getUser();
        if (existingUser != null) {
            List<Role> roles = existingUser.getRole();
            if (roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
                BeanUtils.copyProperties(user, existingUser); // copy properties from new to existing object, excluding id
                return userRepository.save(existingUser);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCreditCard(Long creditCardId) {
        User userHelper = getUser.getUser();
        // Check if user exists
        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if credit card exists and belongs to the user
        CreditCard creditCardToBeDeleted = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new IllegalArgumentException("CreditCard Not found"));

        if (!user.getCreditCards().contains(creditCardToBeDeleted)) {
            throw new IllegalArgumentException("User does not own this credit card");
        }

        // Delete the credit card
        creditCardRepository.deleteById(creditCardId);

        // Remove the credit card from the user's list
        user.getCreditCards().remove(creditCardToBeDeleted);
    }

    @Override
    public void deleteBillingAddressById(Long billingAddressId) {
        // the relationship b/n user and address have to be one to one
        User userHelper = getUser.getUser();

        // Check if user exists
        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //CHeck if the address is found and belongs to the user
        Address addressToBeDeleted = addressRepository.findById(billingAddressId).orElseThrow(() ->
                new IllegalArgumentException("Address Not Found"));
        if (!user.getBillingAddress().equals(addressToBeDeleted)) {
            throw new IllegalArgumentException("User does not belong this to this billing address");
        }
        addressRepository.deleteById(billingAddressId);
    }

    @Override
    public Address updateBillingAddress(Long billingAddressId, Address address) {
        User userHelper = getUser.getUser();

        // Check if user exists
        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //Check if the address is found and belongs to the user
        Address addressToBeUpdated = addressRepository.findById(billingAddressId).orElseThrow(() ->
                new IllegalArgumentException("Address Not Found"));

        if (!user.getBillingAddress().equals(addressToBeUpdated)) {
            throw new IllegalArgumentException("User does not belong this to this billing address");
        }
        Address existingAddress = user.getBillingAddress();
        if (existingAddress != null) {
            // Update the necessary fields of the address
            existingAddress.setStreet(address.getStreet());
            existingAddress.setCity(address.getCity());
            existingAddress.setState(address.getState());
            existingAddress.setZipCode(address.getZipCode());
            existingAddress.setCountry(address.getCountry());
            // Save the updated address
            return userRepository.save(user).getBillingAddress();
        }
        return null;
    }


    @Override
    public Address updateShippingAddress(Long shippingAddressId, Address address) {
        User userHelper = getUser.getUser();

        // Check if user exists
        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //Find the address to be updated in the user's shipping addresses
        Address addressToBeUpdated = user.getShippingAddresses()
                .stream()
                .filter(a -> a.getId().equals(shippingAddressId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Address Not Found"));

        // Update the necessary fields of the address
        addressToBeUpdated.setStreet(address.getStreet());
        addressToBeUpdated.setCity(address.getCity());
        addressToBeUpdated.setState(address.getState());
        addressToBeUpdated.setZipCode(address.getZipCode());
        addressToBeUpdated.setCountry(address.getCountry());

        // Save the updated address
        return userRepository.save(user).getShippingAddresses()
                .stream()
                .filter(a -> a.getId().equals(shippingAddressId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Order> getAllOrderByUserId() {
        User userHelper = getUser.getUser();

        //get the current user
        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return orderRepository.findOrderByUserId(user.getId());
    }

    @Override
    public List<Order> getOrderByItemId(Long itemId) {// what if there are multiple orders with the same item id
        //get the current user
        User userHelper = getUser.getUser();

        User user = userRepository.findById(userHelper.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderRepository.findByItemIdAndUserId(itemId, user.getId());
    }


}
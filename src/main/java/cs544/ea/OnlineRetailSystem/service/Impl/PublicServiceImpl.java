package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.*;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private  final ItemRepository itemRepository;

    @Autowired
    GetUser getUser;

    public PublicServiceImpl(UserRepository userRepository, CreditCardRepository creditCardRepository,
                             AddressRepository addressRepository, OrderRepository orderRepository,
                             ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.itemRepository=itemRepository;
    }


    @Override
    public List<Item> getAllItems() {
       System.out.println("the current active user is :"+getUser.getUser().getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("from inside: "+currentPrincipalName);
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

        address.setAddressType(AddressType.SHIPPING);
        getUser.getUser().getShippingAddresses().add(address);

        return userRepository.save(getUser.getUser()).getShippingAddresses()
                .stream()
                .filter(a -> a.equals(address))
                .findFirst()
                .orElse(null);
    }




    @Override
    public Address addBillingAddress(Address address) {

        address.setAddressType(AddressType.BILLING);
        getUser.getUser().setBillingAddress(address);
        return userRepository.save(getUser.getUser()).getBillingAddress();
    }

    @Override
    @Transactional
    public List<CreditCard> addCreditCard(CreditCard creditCard) {
        User user = userRepository.findById(getUser.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getCreditCards().add(creditCard);
        userRepository.save(user);
        return user.getCreditCards();
       // return creditCardRepository.save(creditCard);
    }

    @Transactional
    public CreditCard updateCreditCardById(Long cardId, CreditCard newCardData) {
        Long userId=getUser.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        CreditCard existingCard = user.getCreditCards().stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CreditCard not found"));

        BeanUtils.copyProperties(newCardData, existingCard);//will copy all non-null properties from the source bean to the target bean
        return creditCardRepository.save(existingCard);
    }

    @Override
    @Transactional
    public void deleteCreditCardById(Long creditCardId) {

        // Check if user exists
        User user = userRepository.findById(getUser.getUser().getId())
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
    public List<CreditCard> getCreditCardByUserId() {
        Long userId=getUser.getUser().getId();
        return userRepository.findCreditCardByCustomerId(userId);
    }


    @Override
    public User updateCustomer(User user) {

        Optional<User> existingUser = userRepository.findById(getUser.getUser().getId());
        if (existingUser.isPresent()) {
            List<Role> roles = existingUser.get().getRole();
            if (roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
                BeanUtils.copyProperties(user, existingUser.get()); // copy properties from new to existing object, excluding id
                return userRepository.save(existingUser.get());
            }
        }
        return null;
    }


    @Override
    public void deleteBillingAddressById(Long billingAddressId) {
        // the relationship b/n user and address have to be one to one

        // Check if user exists
        User user = userRepository.findById(getUser.getUser().getId())
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

        // Check if user exists
        User user = userRepository.findById(getUser.getUser().getId())
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

        // Check if user exists
        User user = userRepository.findById(getUser.getUser().getId())
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
        //get the current user
        User user = userRepository.findById(getUser.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return orderRepository.findOrderByUserId(user.getId());
    }

    @Override
    public List<Order> getOrderByItemId(Long itemId) {// what if there are multiple orders with the same item id(reason why it returns List)
        //get the current user
        User user = userRepository.findById(getUser.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderRepository.findByItemIdAndUserId(itemId, user.getId());
    }

    @Override
    public Order getOrderByOrderId(Long orderId) {
       Long userId= getUser.getUser().getId();
       Optional<Order> order=orderRepository.findOrderByUserIdAndOrderId(userId,orderId);
        return order.orElse(null);
    }

}
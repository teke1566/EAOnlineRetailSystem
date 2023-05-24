package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import cs544.ea.OnlineRetailSystem.repository.CreditCardRepository;
import cs544.ea.OnlineRetailSystem.repository.LineItemRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository customerRepository;
    private final CreditCardRepository creditCardRepository;
    private final CartRepository cartRepository;

    private final LineItemRepository lineItemRepository;
@Autowired
    public CustomerServiceImpl(UserRepository customerRepository, CreditCardRepository creditCardRepository
            , CartRepository cartRepository, LineItemRepository lineItemRepository) {
        this.customerRepository = customerRepository;
        this.creditCardRepository = creditCardRepository;
        this.cartRepository = cartRepository;
        this.lineItemRepository = lineItemRepository;
    }

    @Override
    public List<User> getAllCustomers() {
        return customerRepository.findUsersByRole(Roles.CUSTOMER);
    }

    @Override
    public User getCustomerById(Long customerId) {
        Optional<User> user = customerRepository.findById(customerId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getRole();
            if (roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
                return user.get();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCustomerById(Long customerId) {
        Optional<User> user = customerRepository.findById(customerId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getRole();
            if (roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
                Cart carts = cartRepository.findCartByCustomerId(customerId);
                lineItemRepository.deleteLineItemsByCartId(carts.getId());
                customerRepository.deleteById(customerId);
                cartRepository.deleteCartByCustomerId(customerId);
            } else {
                System.out.println("User Role is Not Customer");
            }
        } else {
            System.out.println("couldn't find user with the given id");
        }
    }


    @Override
    public User addCustomer(User user) {
        return customerRepository.save(user);
    }


    @Override
    public User updateCustomer(Long customerId, User user) {
        Optional<User> existingUser = customerRepository.findById(customerId);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User with id " + customerId + " not found.");
        }
        List<Role> roles = existingUser.get().getRole();
        if (!roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
            throw new IllegalArgumentException("User with id " + customerId + " is not a customer.");
        }
        BeanUtils.copyProperties(user, existingUser.get());
        try {
            return customerRepository.save(existingUser.get());
        } catch (DataAccessException dae) {
            log.error("Error occurred while updating customer", dae);
            throw dae;
        }
    }


    public Address getCustomerDefaultShippingAddress(Long customerId) {
        User user = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Address defaultShippingAddress = user.getDefaultShippingAddress();
        if (defaultShippingAddress == null) {
            throw new EntityNotFoundException("Default shipping address not set for customer");
        }

        return defaultShippingAddress;
    }

//    @Override
//    public CreditCard addCreditCard(CreditCard creditCard) {
//        return creditCardRepository.save(creditCard);
//    }
    public CreditCard getCreditCardById(Long creditCardId){
        Optional<CreditCard> creditCard= creditCardRepository.findById(creditCardId);
        return creditCard.orElse(null);
    }


    @Override
    public void deleteCreditCardById(Long creditCardId) {
        creditCardRepository.deleteById(creditCardId);
    }

    @Override
    public CreditCard updateCreditCard(Long creditCardId, CreditCard creditCard) {
        CreditCard existingCreditCard = getCreditCardById(creditCardId);
        if (existingCreditCard != null) {
            BeanUtils.copyProperties(creditCard, existingCreditCard);//copy file from new to existing object
            return creditCardRepository.save(existingCreditCard);
        }
        return null;
    }

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

//    @Override
//    public Address addShippingAddress(Address address) { //this is done in PublicService
//        User user = getUser(); // Retrieve the user from the current context, or fetch it from the repository
//        address.setAddressType(AddressType.SHIPPING);
//        user.getShippingAddresses().add(address);
//        return customerRepository.save(user).getShippingAddresses()
//                .stream()
//                .filter(a -> a.equals(address))
//                .findFirst()
//                .orElse(null);
//    }


    @Override
    public Address updateShippingAddress(Long shippingAddressId, Address address) {
        Optional<User> optionalUser = customerRepository.findById(shippingAddressId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Address> shippingAddresses = user.getShippingAddresses();
            Address existingAddress = shippingAddresses.stream()
                    .filter(a -> a.getId().equals(shippingAddressId))
                    .findFirst()
                    .orElse(null);
            if (existingAddress != null) {
                // Update the necessary fields of the address
                existingAddress.setStreet(address.getStreet());
                existingAddress.setCity(address.getCity());
                existingAddress.setState(address.getState());
                existingAddress.setZipCode(address.getZipCode());
                existingAddress.setCountry(address.getCountry());
                // Save the updated address
                return customerRepository.save(user).getShippingAddresses()
                        .stream()
                        .filter(a -> a.equals(existingAddress))
                        .findFirst()
                        .orElse(null);
            }
        }
        return null;
    }


    @Override
    public void deleteShippingAddressById(Long shippingAddressId) {
        customerRepository.deleteById(shippingAddressId);
    }

    @Override
    public List<Address> getAllBillingAddress() {
        List<Address> billingAddresses = new ArrayList<>();

        List<User> customers = customerRepository.findAll();
        for (User customer : customers) {
            Address billingAddress = customer.getBillingAddress();
            if (billingAddress != null) {
                billingAddresses.add(billingAddress);
            }
        }

        return billingAddresses;
    }

//    @Override
//    public Address addBillingAddress(Address address) {
//        User user = getUser(); // Retrieve the user from the current context, or fetch it from the repository
//        address.setAddressType(AddressType.BILLING);
//        user.setBillingAddress(address);
//        return customerRepository.save(user).getBillingAddress();
//    }
//    private User getUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getName();
//            return customerRepository.findByname(username);
//        }
//
//        return null;
//    }


    @Override
    public void deleteBillingAddressById(Long billingAddressId) {
        customerRepository.deleteById(billingAddressId);
    }

    @Override
    public Address updateBillingAddress(Long billingAddressId, Address address) {
        Optional<User> optionalUser = customerRepository.findById(billingAddressId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Address existingAddress = user.getBillingAddress();
            if (existingAddress != null) {
                // Update the necessary fields of the address
                existingAddress.setStreet(address.getStreet());
                existingAddress.setCity(address.getCity());
                existingAddress.setState(address.getState());
                existingAddress.setZipCode(address.getZipCode());
                existingAddress.setCountry(address.getCountry());
                // Save the updated address
                return customerRepository.save(user).getBillingAddress();
            }
        }
        return null;
    }

    @Override
    public List<Address> getAllShippingAddress() {
        List<User> customers = customerRepository.findAll();
        List<Address> shippingAddresses = new ArrayList<>();

        for (User customer : customers) {
            List<Address> addresses = customer.getShippingAddresses();
            shippingAddresses.addAll(addresses);
        }

        return shippingAddresses;
    }
}
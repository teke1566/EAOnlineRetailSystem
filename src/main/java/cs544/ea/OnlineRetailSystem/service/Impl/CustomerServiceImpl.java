package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.repository.*;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository customerRepository;
    private final CreditCardRepository creditCardRepository;
    private final CartRepository cartRepository;

    private final LineItemRepository lineItemRepository;

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final AddressRepository addressRepository;

    public CustomerServiceImpl(UserRepository customerRepository,
                               CreditCardRepository creditCardRepository,
                               CartRepository cartRepository,
                               LineItemRepository lineItemRepository,
                               OrderRepository orderRepository,
                               ReviewRepository reviewRepository, AddressRepository addressRepository) {
        this.customerRepository=customerRepository;
        this.lineItemRepository=lineItemRepository;
        this.cartRepository=cartRepository;
        this.creditCardRepository=creditCardRepository;
        this.orderRepository=orderRepository;
        this.reviewRepository=reviewRepository;

        this.addressRepository = addressRepository;
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
    public void deleteCustomerById(Long customerId) throws Exception {
        Optional<User> user = customerRepository.findById(customerId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getRole();
            if (roles.stream().anyMatch(role -> role.getRole().equals(Roles.CUSTOMER))) {
                Cart carts = cartRepository.findCartByCustomerId(customerId);
                List<Order> order= orderRepository.findOrderByUserId(customerId);
                List<Review> reviews = reviewRepository.findByUserId(customerId);
                if (carts == null || carts.getId() == null || order.isEmpty() || reviews.isEmpty()) {
                    customerRepository.deleteById(customerId);
                } else {
                    cartRepository.deleteCartByCustomerId(customerId);
                    lineItemRepository.deleteLineItemsByCartId(carts.getId());
                    orderRepository.deleteOrderByCustomerId(customerId);
                    reviewRepository.deleteByUserId(customerId);
                    customerRepository.deleteById(customerId);
                }
            } else {
                throw new Exception("User Role is Not Customer");
            }
        } else {
            throw new Exception("Couldn't find user with the given id");
        }
    }



    @Override
    public User addCustomer(User user) {
        return customerRepository.save(user);
    }


    @Override
    @Transactional
    public User updateCustomer(Long customerId, User user) {
        Optional<User> existingUser = customerRepository.findById(customerId);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User with id " + customerId + " not found.");
        }
        List<Role> roles = existingUser.get().getRole();
        if (!roles.stream().anyMatch(role -> role.getRole() == Roles.CUSTOMER)) {
            throw new IllegalArgumentException("User with id " + customerId + " is not a customer.");
        }
        BeanUtils.copyProperties(user, existingUser.get(),"id");
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
            BeanUtils.copyProperties(creditCard, existingCreditCard,"id");//copy file from new to existing object
            return creditCardRepository.save(existingCreditCard);
        }
        return null;
    }

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }



@Override
    public Address updateShippingAddress(Long shippingAddressId, Address updatedAddress) {
        Address shippingAddress = addressRepository.findByIdAndAddressType(shippingAddressId, AddressType.SHIPPING);

        if (shippingAddress == null) {
            throw new EntityNotFoundException("Shipping address not found");
        }

        // Update the fields of the existing shipping address
        shippingAddress.setStreet(updatedAddress.getStreet());
        shippingAddress.setCity(updatedAddress.getCity());
        shippingAddress.setState(updatedAddress.getState());
        shippingAddress.setZipCode(updatedAddress.getZipCode());
        shippingAddress.setCountry(updatedAddress.getCountry());

        return addressRepository.save(shippingAddress);
    }



    @Override
    public void deleteShippingAddressById(Long shippingAddressId) {
        addressRepository.deleteByIdAndAddressType(shippingAddressId, AddressType.SHIPPING);
    }
    @Override
    public void deleteBillingAddressById(Long billingAddressId) {
        addressRepository.deleteByIdAndAddressType(billingAddressId, AddressType.BILLING);
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
    public  Address updateBillingAddress(Long billingAddressId, Address updatedAddress) {
        Address billingAddress = addressRepository.findByIdAndAddressType(billingAddressId, AddressType.BILLING);

        if (billingAddress == null) {
            throw new EntityNotFoundException("Billing address not found");
        }

        // Update the fields of the existing billing address
        billingAddress.setStreet(updatedAddress.getStreet());
        billingAddress.setCity(updatedAddress.getCity());
        billingAddress.setState(updatedAddress.getState());
        billingAddress.setZipCode(updatedAddress.getZipCode());
        billingAddress.setCountry(updatedAddress.getCountry());

        return addressRepository.save(billingAddress);
    }
}
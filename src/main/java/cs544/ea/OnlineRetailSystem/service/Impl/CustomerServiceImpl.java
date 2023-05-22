package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.AddressType;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.CreditCardRepository;
import cs544.ea.OnlineRetailSystem.repository.CustomerRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import org.springframework.beans.BeanUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
     private UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CreditCardRepository creditCardRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,CreditCardRepository creditCardRepository) {
        this.customerRepository = customerRepository;
        this.creditCardRepository= creditCardRepository;
    }

    @Override
    public User addCustomer(User user) {
        return customerRepository.save(user);
    }

    @Override
    public User getCustomerById(Long customerId) {
        Optional<User> customer = customerRepository.findById(customerId);
        return customer.orElse(null);
    }

    @Override
    public List<User> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public User updateCustomer(Long customerId, User user) {
        User existingCustomer = getCustomerById(customerId);
        if(existingCustomer != null) {
            BeanUtils.copyProperties(user, existingCustomer, "userId");//copy file from new to existing object
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }
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
        if(existingCreditCard != null) {
            BeanUtils.copyProperties(creditCard, existingCreditCard);//copy file from new to existing object
            return creditCardRepository.save(existingCreditCard);
        }
        return null;
    }

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public Address addShippingAddress(Address address) {
        User user = getUser(); // Retrieve the user from the current context, or fetch it from the repository
        address.setAddressType(AddressType.SHIPPING);
        user.getShippingAddresses().add(address);
        return customerRepository.save(user).getShippingAddresses()
                .stream()
                .filter(a -> a.equals(address))
                .findFirst()
                .orElse(null);
    }


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
    @Override
    public Address addBillingAddress(Address address) {
        User user = getUser(); // Retrieve the user from the current context, or fetch it from the repository
        address.setAddressType(AddressType.BILLING);
        user.setBillingAddress(address);
        return customerRepository.save(user).getBillingAddress();
    }
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findByname(username);
        }

        return null;
    }








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
package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.CreditCardRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import org.springframework.beans.BeanUtils;
//org.springframework.beans.BeanUtils.copyProperties(Object source, Object target)
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository customerRepository;
    private final CreditCardRepository creditCardRepository;

    public CustomerServiceImpl(UserRepository customerRepository,CreditCardRepository creditCardRepository) {
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
        return null;
    }

    @Override
    public Address updateShippingAddress(Long shippingAddressId, Address address) {
        return null;
    }

    @Override
    public void deleteShippingAddressById(Long shippingAddressId) {

    }

    @Override
    public List<Address> getAllShippingAddress() {
        return null;
    }

    @Override
    public Address addBillingAddress(Address address) {
        return null;
    }

    @Override
    public void deleteBillingAddressById(Long billingAddressId) {

    }

    @Override
    public Address updateBillingAddress(Long billingAddressId, Address address) {
        return null;
    }

    @Override
    public List<Address> getAllBillingAddress() {
        return null;
    }
}
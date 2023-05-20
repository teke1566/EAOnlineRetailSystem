package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    @OneToOne
    private Address billingAddress;
    @OneToMany
    @JoinColumn(name = "shippingaddressId")
    private List<Address> shippingAddresses;
    @OneToMany
    @JoinColumn(name = "creditCardId")
    private List<CreditCard> creditCards;
}

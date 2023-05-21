package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    @OneToOne
    private Address billingAddress;
    @OneToMany
    @JoinColumn(name = "userShippingAddressId")
    private List<Address> shippingAddresses;
    @OneToMany
    @JoinColumn(name = "userCreditCardId")
    private List<CreditCard> creditCards;

    @Enumerated
    private Roles roles;
}

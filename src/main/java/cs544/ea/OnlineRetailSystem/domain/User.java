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
    private Long id;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String password;


    @OneToOne
    private Address billingAddress;
    @OneToMany
    @JoinColumn(name = "userShippingAddressId")
    private List<Address> shippingAddresses;
    @OneToMany
    @JoinColumn(name = "userCreditCardId")
    private List<CreditCard> creditCards;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
}

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
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String password;


    @ManyToOne
    @JoinColumn(name = "billingaddress")
    private Address billingAddress;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "useraddress",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> shippingAddresses;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<CreditCard> creditCards;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
}

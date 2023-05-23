package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "addresstype")
    private AddressType addressType;

    private String street;
    private String city;
    private String state;
    @Column(name = "zipcode")
    private String zipCode;
    private String country;

}

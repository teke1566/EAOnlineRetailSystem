package cs544.ea.OnlineRetailSystem.DTO;

import java.util.List;

import cs544.ea.OnlineRetailSystem.domain.CreditCard;
import cs544.ea.OnlineRetailSystem.domain.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private AddressResponse billingAddress;
    private List<AddressResponse> shippingAddresses;
    private List<CreditCard> creditCards;
    private Role roles;
}


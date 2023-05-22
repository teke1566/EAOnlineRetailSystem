package cs544.ea.OnlineRetailSystem.DTO;

import cs544.ea.OnlineRetailSystem.domain.AddressType;
import lombok.Data;

@Data
public class AddressResponse {
    private Long id;
    private AddressType addressType;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}

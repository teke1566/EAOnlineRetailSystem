package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByIdAndAddressType(Long billingAddressId, AddressType billing);

    void deleteByIdAndAddressType(Long shippingAddressId, AddressType shipping);
}

package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Address;
import cs544.ea.OnlineRetailSystem.domain.AddressType;
import cs544.ea.OnlineRetailSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<User,Long> {
   // List<Address> findAllByAddressType(AddressType addressType);

}

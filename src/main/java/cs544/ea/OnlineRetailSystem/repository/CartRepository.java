package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

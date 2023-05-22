package cs544.ea.OnlineRetailSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cs544.ea.OnlineRetailSystem.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

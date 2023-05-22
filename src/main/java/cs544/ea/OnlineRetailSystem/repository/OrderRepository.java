package cs544.ea.OnlineRetailSystem.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cs544.ea.OnlineRetailSystem.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query("select order from Order order where order.customer.id = :userId")
	public List<Order> getOrderByUserId(Long userId);
}



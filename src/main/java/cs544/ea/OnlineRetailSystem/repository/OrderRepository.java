package cs544.ea.OnlineRetailSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cs544.ea.OnlineRetailSystem.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> getOrderByUserId(Long userId);

}

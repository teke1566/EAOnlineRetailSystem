package cs544.ea.OnlineRetailSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findOrdersByStatus(OrderStatus orderStatus);
	
	@Query("select order from Order order where order.customer.id = :userId")
	public List<Order> findOrdersByUserId(Long userId);
	
	@Query("select order from Order order where order.customer.id = :userId and order.status = :orderStatus")
	public List<Order> findOrdersByUserIdAndStatus(Long userId, OrderStatus orderStatus);
	
	@Query("select order from Order order where order.customer.id = :userId and order.id = :orderId")
	public Optional<Order> findOrderByUserIdAndOrderId(Long userId, Long orderId);
}

package cs544.ea.OnlineRetailSystem.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findOrdersByStatus(OrderStatus orderStatus);
	
	@Query("select order from Order order where order.customer.id = :userId")
	public List<Order> findOrdersByUserId(Long userId);
	
	@Query("select order from Order order where order.customer.id = :userId and order.status = :orderStatus")
	public List<Order> findOrdersByUserIdAndStatus(Long userId, OrderStatus orderStatus);
	
	@Query("select order from Order order where order.customer.id = :userId and order.id = :orderId")
	public Optional<Order> findOrderByUserIdAndOrderId(Long userId, Long orderId);


	@Query("select o from Order o where o.customer.id=:userId")
	public List<Order> findOrderByUserId(@Param("userId") Long userId);


	@Query("select o from Order o JOIN o.lineItems l where l.item.itemId=:itemId")
	public Order findByItemId(Long itemId);

	@Query("select o from Order o JOIN o.lineItems l where l.item.itemId = :itemId and o.customer.id = :userId")
	List<Order> findByItemIdAndUserId(Long itemId, Long userId); //will return order based on item id that belongs to specific User
	@Transactional
	@Modifying
	@Query("delete from Order o where  o.customer.id=:customerId")
	void deleteOrderByCustomerId(Long customerId);

}

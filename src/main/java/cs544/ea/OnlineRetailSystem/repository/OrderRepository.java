package cs544.ea.OnlineRetailSystem.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cs544.ea.OnlineRetailSystem.domain.Order;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select order from Order order where order.customer.id = :userId")
	public List<Order> getOrderByUserId(Long userId);

=======
public interface OrderRepository {
>>>>>>> b307f5a3d918c122cd5b25672bfee48316f31f44
}

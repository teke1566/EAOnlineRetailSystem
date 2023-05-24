package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.OrderStatus;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindOrderByUserIdAndStatus(){
        //Arrange
        User user= new User();
        entityManager.persistAndFlush(user);

        Order order= new Order();
        order.setCustomer(user);
        order.setStatus(OrderStatus.DELIVERED);
        entityManager.persistAndFlush(order);

        // Act
        List<Order> foundOrders = orderRepository.findOrdersByUserIdAndStatus(user.getId(), OrderStatus.DELIVERED);

        //Assert
        assertThat(foundOrders).isNotNull();
        assertThat(foundOrders.get(0)).isEqualTo(order);
    }

    @Test
    public void testDeleteOrderByCustomerId() {
        // first persist an Order with a customer ID
        User customer = new User();
        customer.setId(1L);
        entityManager.merge(customer);

        Order order = new Order();
        order.setCustomer(customer);
        entityManager.merge(order);
        entityManager.flush();

        // check that an order is present for the customer
        List<Order> orders = orderRepository.findOrderByUserId(customer.getId());
        assertThat(orders).isNotEmpty();

        // now delete the order
        orderRepository.deleteOrderByCustomerId(customer.getId());
        entityManager.flush();

        // check that the order is deleted
        orders = orderRepository.findOrderByUserId(customer.getId());
        assertThat(orders).isEmpty();
    }
}

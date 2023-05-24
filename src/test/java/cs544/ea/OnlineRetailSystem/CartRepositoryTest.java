package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.CartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testFindCartByCustomerId() {
        // Arrange
        User user = new User();
        entityManager.persistAndFlush(user);

        Cart cart = new Cart();
        cart.setCustomer(user);
        entityManager.persistAndFlush(cart);

        // Act
        Cart foundCart = cartRepository.findCartByCustomerId(user.getId());

        // Assert
        assertThat(foundCart).isNotNull();
        assertThat(foundCart).isEqualTo(cart);
    }

}

package cs544.ea.OnlineRetailSystem;

/*
 * Testing repositories in Spring Boot often involves
 *  writing integration tests rather than unit tests.
 * This is because repository methods generally interact
 *  with the database, and mocking that interaction can
 * be challenging and may not provide a lot of value.
 * In these cases, you'd often use an in-memory database like H2 for testing
 * */

/*
* Arrange: Set up the conditions for your test.
* This usually means creating and saving instances of your entities.
Act: Call the method you're trying to test.
Assert: Check that the method returned the expected result.*/

import cs544.ea.OnlineRetailSystem.domain.Role;
import cs544.ea.OnlineRetailSystem.domain.Roles;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        entityManager.persistAndFlush(user);
        User foundUser = userRepository.findByEmail("test@example.com");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    public void testFindByName(){
        //Arrange
        User user= new User();
        user.setName("customer");
        entityManager.persistAndFlush(user);

        //Act
        User foundUser= userRepository.findByname("customer");


        //Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(user.getName());
    }

    @Test
    public void testFindUsersByRole(){
        //Arrange
        Role role= new Role();
        role.setRole(Roles.CUSTOMER);
        entityManager.persistAndFlush(role);

        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Collections.singletonList(role));
        entityManager.persistAndFlush(user);

        //Act
        List<User> foundUsers =userRepository.findUsersByRole(Roles.CUSTOMER);

        //Assert
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).contains(user);
    }
}

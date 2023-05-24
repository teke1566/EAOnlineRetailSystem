package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Role;
import cs544.ea.OnlineRetailSystem.domain.Roles;
import cs544.ea.OnlineRetailSystem.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByRole(){
        //Arrange
        Role role= new Role();
        role.setRole(Roles.CUSTOMER);
        entityManager.persistAndFlush(role);

        //Act
        Role foundRole= roleRepository.findByRole(Roles.CUSTOMER);

        //Assert
        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getRole()).isEqualTo(Roles.CUSTOMER);
    }
}

package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Role;
import cs544.ea.OnlineRetailSystem.domain.Roles;
import cs544.ea.OnlineRetailSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User findByname(String username);


    @Query("SELECT u FROM User u JOIN u.role r WHERE r.role = :role") //used to select User with Customer Role
    List<User> findUsersByRole(@Param("role") Roles role);
    @Query("select u from User u join u.role r where r.role <> 'ADMIN'")
    List<User> findAllExceptAdmin();


    @Query("select u from User u where u.email=:email")
    User findUserByEmailAddress(String email);
}

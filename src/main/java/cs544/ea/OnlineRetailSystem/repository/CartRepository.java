package cs544.ea.OnlineRetailSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import jakarta.transaction.Transactional;


public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.customer.id = :customerId")
    void deleteCartByCustomerId(@Param("customerId") Long customerId);


//    @Query("SELECT c FROM Cart c JOIN c.lineItems li WHERE li.item.id = :itemId")
//    List<Cart> findByItemId(@Param("itemId") Long itemId);


    @Query("select c from Cart c where c.customer.id = :customerId")
    Cart findCartByCustomerId(Long customerId);

}

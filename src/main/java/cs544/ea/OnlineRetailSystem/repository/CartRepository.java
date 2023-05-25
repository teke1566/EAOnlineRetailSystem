package cs544.ea.OnlineRetailSystem.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import cs544.ea.OnlineRetailSystem.domain.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.customer.id=:customerId")
    void deleteCartByCustomerId(@Param("customerId") Long customerId);

//    @Modifying
//    @Transactional
//    @Query("SELECT c FROM Cart c JOIN c.items i WHERE i.id = :itemId")
//    List<Cart> findByItemId(@Param("itemId") Long itemId);


    @Query("select c from Cart  c where c.customer.id=:customerId")
    Cart findCartByCustomerId(Long customerId);



    @Query("SELECT c FROM Cart c JOIN c.lineItems li WHERE li.item.id = :itemId")
    List<Cart> findByItemId(@Param("itemId") Long itemId);



}

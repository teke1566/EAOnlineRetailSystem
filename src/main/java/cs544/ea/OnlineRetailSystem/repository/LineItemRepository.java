package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.LineItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LineItemRepository extends JpaRepository<LineItem,Long> {
    @Modifying
    @Transactional
    @Query("delete from LineItem l where l.cart.id=:cartId")
    void deleteLineItemsByCartId(Long cartId);

}

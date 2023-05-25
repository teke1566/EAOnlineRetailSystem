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


    @Query("DELETE FROM LineItem l WHERE l.item.itemId = :itemId")//This deletes the associated line items in the LineItem entity table that have a matching itemId.
    void deleteByItemId(Long itemId);



    @Transactional
    @Query("delete from LineItem li where li.cart.id = :cartId and li.lineItemId = :lineItemId")
    void deleteLineItemByCartIdAndLineItemId(Long cartId, Long lineItemId);

}

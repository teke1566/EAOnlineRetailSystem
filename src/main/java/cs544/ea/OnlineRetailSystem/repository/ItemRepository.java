package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    List<Item> findByNameContainingIgnoreCase(String keyword);

    @Query("SELECT i FROM Item i WHERE i.merchant.id = :merchantId")
    List<Item> findItemsByMerchantId(@Param("merchantId") Long merchantId);
    @Query("select i from Item i where i.itemId=:itemId AND i.merchant.id=:userId")
    Item findItemByItemIdAndUserId(Long itemId,Long userId);

    @Query("SELECT r FROM Review r WHERE r.item.merchant.id = :userId AND r.item.itemId = :itemId")
    List<Review> getReviewsByItemIdAndMerchantId(@Param("userId") Long userId, @Param("itemId") Long itemId);


}

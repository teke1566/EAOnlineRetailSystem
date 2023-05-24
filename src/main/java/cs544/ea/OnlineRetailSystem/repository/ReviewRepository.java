package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.item.itemId=:itemId")
    List<Review> findByItemId(Long itemId);

    @Modifying
    @Transactional
    @Query("delete from Review r where r.buyer.id=:userId")
   public void deleteByUserId(Long userId);
    @Query("select r from Review r where r.buyer.id=:userId")
   List<Review> findByUserId(Long userId);
}

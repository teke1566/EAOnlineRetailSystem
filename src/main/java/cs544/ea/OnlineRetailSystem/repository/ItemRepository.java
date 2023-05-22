package cs544.ea.OnlineRetailSystem.repository;

import cs544.ea.OnlineRetailSystem.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

    List<Item> findByNameContainingIgnoreCase(String Keyword);

}

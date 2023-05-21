package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemService  {
    List<Item> getAllItems();
    Item getItemById(Long item);

    Item addItem(Item item);
    Item updateItem(Long itemId, Item item);

    void deleteItem(Long itemId);

    List<Item> searchItems(String Keyword);

}

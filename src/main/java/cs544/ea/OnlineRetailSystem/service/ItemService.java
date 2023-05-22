package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemService  {
    List<Item> getAllItems();
    Item getItemById(Long item);

    Item addItem(Item item);
    Item updateItem(Long itemId, Item item);

    void deleteItem(Long itemId);

    List<Item> searchItems(String Keyword);

    Review addReview(Long itemId, Review review); // this is added because we need it for the itemController since we don't have a ReviewController

    List<Review> getReviewByItemId(Long itemId);// this is added because we need it for the itemController since we don't have a ReviewController
}

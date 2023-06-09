package cs544.ea.OnlineRetailSystem.service;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemService  {
//    List<Item> getAllItems();
    Item getItemById(Long item);//this item has to belong to the Merchant which is the owner of the product

    Item addItem(Item item);
    Item updateItem(Long itemId, Item item);

    void deleteItem(Long itemId);

    List<Item> searchItemByName(String Keyword);//it was Items so we change not to do

    Review addReview(Long itemId, Review review); // this is added because we need it for the itemController since we don't have a ReviewController

    List<Review> getReviewByItemId(Long itemId);// this is added because we need it for the itemController since we don't have a ReviewController

    List<Review> getAllReviewByCustomerId();

    List<Item> getAllItemByMerchantId(Long userId);//not to do that

    List<Order> getAllOrder(Long userId);//to do

    public Order getOrderById(Long id);//it will return the //


    List<Item> getAllItems();
}

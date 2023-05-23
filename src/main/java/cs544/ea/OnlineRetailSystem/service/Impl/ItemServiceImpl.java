package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Order;
import cs544.ea.OnlineRetailSystem.domain.Review;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ReviewRepository reviewRepository;
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ReviewRepository reviewRepository) {
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long item) {
        return itemRepository.findById(item).orElseThrow(()-> new IllegalArgumentException("Item not found"));
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long itemId, Item item) {
        return itemRepository.findById(itemId)
                .map(existingItem -> {
                    existingItem.setName(item.getName());
                    existingItem.setDescription(item.getDescription());
                    existingItem.setPrice(item.getPrice());
                    existingItem.setImage(item.getImage());
                    existingItem.setBarcode(item.getBarcode());
                    existingItem.setQuantityInStock(item.getQuantityInStock());
                    return itemRepository.save(existingItem);
                }).orElseThrow(()-> new IllegalArgumentException("Item not found"));
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);

    }

    @Override
    public List<Item> searchItemByName(String Keyword) {
        return itemRepository.findByNameContainingIgnoreCase(Keyword);
    }



    @Override
    public Review addReview(Long itemId, Review review) {
        Item item = getItemById(itemId);
        review.setItem(item); // Set the item of the review
        return reviewRepository.save(review); // Save and return the review
    }

    @Override
    public List<Review> getReviewByItemId(Long itemId) {
        return reviewRepository.findByItemId(itemId); // Return reviews by item id
    }

    @Override
    public List<Item> getAllItemByMerchantId(Long userId) { //to do by other
        return null;
    }

    @Override
    public List<Order> getAllOrder(Long userId) {//to do by other
        return null;
    }

    @Override
    public Order getOrderById(Long id, Long merchantId) {//to do by other
        return null;
    }

    public List<Item> searchItems(String keyword) {
        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }
}

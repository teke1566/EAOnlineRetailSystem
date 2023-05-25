package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.repository.OrderRepository;
import cs544.ea.OnlineRetailSystem.repository.UserRepository;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.repository.ReviewRepository;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ReviewRepository reviewRepository;

    private final PublicService publicService;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final GetUser user;
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ReviewRepository reviewRepository, PublicService publicService, UserRepository userRepository, OrderRepository orderRepository, GetUser user) {
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
        this.publicService = publicService;
        this.userRepository = userRepository;
       this.orderRepository = orderRepository;
        this.user = user;
    }

//    @Override
//    public List<Item> getAllItems() {
//        return itemRepository.findAll();
//    }

    @Override
    public Item getItemById(Long item) {
        return itemRepository.findById(item).orElseThrow(()-> new IllegalArgumentException("Item not found"));
    }//this done in publicService


    @Override
    public Item addItem(Item item) {

        User merchantId = user.getUser();

        // Fetch the merchant from the database using the merchantId
        User merchant = userRepository.findById(merchantId.getId())
                .orElseThrow(() -> new IllegalArgumentException("Merchant not found"));

        // Set the merchant for the item
        item.setMerchant(merchant);

        // Save the item
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
    public List<Item> searchItemByName(String keyword) {
        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }


    @Override
    public Review addReview(Long itemId, Review review) {

        User customer = user.getUser();
        List<Order> orders = orderRepository.getOrderByCustomerId(customer.getId());
//we have list of item in orders I have to loop the itemId if it exist  in the order list //then check if the item is deliverd if so the customer can add a review

        for (Order order : orders) {
            List<LineItem> items = order.getLineItems();
            for (LineItem item : items) {
                if (item.getItem().getItemId().equals(itemId)) {
                    if (order.isDelivered(order)) {
                        // The item exists in the order and is delivered
                        // Allow the customer to add a review

                        return reviewRepository.save(review);
                    } else {
                        throw new IllegalStateException("Item is not delivered yet");
                    }
                }
            }
        }

        throw new IllegalArgumentException("Item not found in customer's orders");
    }


    @Override
    public List<Review> getReviewByItemId(Long itemId) {
        return reviewRepository.findByItemId(itemId); // Return reviews by item id
    }

    @Override
    public List<Review> getAllReviewByCustomerId() {
        Long customerId= user.getUser().getId();
        return reviewRepository.findByUserId(customerId);
    }


    @Override

    public List<Item> getAllItemByMerchantId(Long userId) { //i need to check first the item is belong to the merchant and get the item

        User merchant = userRepository.findById(userId).get();

        if(merchant == null){
            return Collections.emptyList();
        }

        return itemRepository.findItemsByMerchantId(merchant.getId());
    }//this need to check the implimentation

    @Override
    public List<Order> getAllOrder(Long userId) {//to do by other
        return null;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.getReferenceById(id);
    }



    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> searchItems(String keyword) {
        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }
}

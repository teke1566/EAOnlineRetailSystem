package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.*;
import cs544.ea.OnlineRetailSystem.helper.GetUser;
import cs544.ea.OnlineRetailSystem.repository.*;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ReviewRepository reviewRepository;

    private final PublicService publicService;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final LineItemRepository lineItemRepository; //the resone I inject this is I need to use the deleteItemIn LineItem repository
    private final GetUser user;
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ReviewRepository reviewRepository, PublicService publicService, UserRepository userRepository, OrderRepository orderRepository, CartRepository cartRepository, LineItemRepository lineItemRepository, GetUser user) {
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
        this.publicService = publicService;
        this.userRepository = userRepository;
       this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.lineItemRepository = lineItemRepository;
        this.user = user;
    }



    @Override
    public Item getItemById(Long item) {
        Long userId= user.getUser().getId();

        return itemRepository.findItemByItemIdAndUserId(item,userId);
    }


    @Override
    public Item addItem(Item item) {

        User merchantId = user.getUser();


        User merchant = userRepository.findById(merchantId.getId())// Fetch the merchant from the database using the merchantId
                .orElseThrow(() -> new IllegalArgumentException("Merchant not found"));


        item.setMerchant(merchant);// Set the merchant for the item

        return itemRepository.save(item);// Save the item

    }

    @Override
    @Transactional
    public Item updateItem(Long itemId, Item item) {
        Long userId = user.getUser().getId();


        Item existingItem=itemRepository.findItemByItemIdAndUserId(itemId, userId);
        BeanUtils.copyProperties(item,existingItem,"id");
        return  itemRepository.save(existingItem);
//                .map(existingItem -> {
//                    existingItem.setName(item.getName());
//                    existingItem.setDescription(item.getDescription());
//                    existingItem.setPrice(item.getPrice());
//                    existingItem.setImage(item.getImage());
//                    existingItem.setBarcode(item.getBarcode());
//                    existingItem.setQuantityInStock(item.getQuantityInStock());
//                    return itemRepository.save(existingItem);
//                }).orElseThrow(()-> new IllegalArgumentException("Item not found"));
    }

    @Override
    @Transactional // the method should be executed within the transaction
    public void deleteItem(Long itemId) {
        // Get the currently logged-in merchant or user
        User currentMerchant = user.getUser();

        // Check if the item exists
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new IllegalArgumentException("Item not found with ID: " + itemId);
        }

        Item item = optionalItem.get();

        // Check if the item belongs to the current merchant
        if (!item.getMerchant().equals(currentMerchant)) {
            throw new IllegalArgumentException("You don't have permission to delete this item.");
        }

        // Delete the line items associated with the item
        lineItemRepository.deleteByItemId(itemId);

        // Delete the item
        itemRepository.deleteById(itemId);

        // Check if the item is still present after deletion
        boolean itemExists = itemRepository.existsById(itemId);

        // Check the deletion status
        if (!itemExists) {
            System.out.println("Item with ID " + itemId + " has been successfully deleted.");
        } else {
            System.out.println("Failed to delete item with ID " + itemId + ".");
        }
    }






    @Override
    public List<Item> searchItemByName(String keyword) {
        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }


    @Override
    public Review addReview(Long itemId, Review review) {

        User customer = user.getUser();
        List<Order> orders = orderRepository.getOrderByCustomerId(customer.getId());
//we have list of item in orders I have to loop the itemId if it exists  in the order list //then check if the item is deliverd if so the customer can add a review

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
    public List<Item> getAllItemByMerchantId() {

        User merchant = user.getUser();

        User user1 = userRepository.findById(merchant.getId()).orElse(null); // Get the merchant by user ID

        if (user1 == null) {
            return Collections.emptyList();
        }
        if (!user1.getRole().contains(merchant)){// Check if the user is a merchant
            throw new IllegalArgumentException("Invalid user type. Expected Merchant.");
        }
        return itemRepository.findItemsByMerchantId(merchant.getId()); // Retrieve the items belonging to the merchant
    }

    @Override
    public List<Order> getAllOrderByMerchantId() {
        Long userId = user.getUser().getId();


     List<Item> items= itemRepository.findItemsByMerchantId(userId);

        List<Order> orderList = null;

        for (Item item:items){
            orderList=orderRepository.findOrdersByStatusAndMerchantIdNotEqual(item.getItemId(),OrderStatus.NEW);
            }
        return orderList ;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public List<Review> getAllReviewByCustomerId() {
        Long customerId= user.getUser().getId();
        return reviewRepository.findByUserId(customerId);
    }

    @Override
    public List<Review> getReviewsByItemIdAndMerchantId(Long userId, Long itemId) {
        return itemRepository.getReviewsByItemIdAndMerchantId(userId, itemId);

    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

}

package cs544.ea.OnlineRetailSystem.controller;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Review;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class MerchantController {

    private final ItemService itemService;
    private final PublicService publicService;



    @Autowired
    public MerchantController(ItemService itemService,
                              PublicService publicService) {
        this.itemService = itemService;
        this.publicService= publicService;
    }

    @GetMapping("/") //this should be just empty e.g. /api/v1/items
    public List<Item> getAllItems(){
        return publicService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }

    @PostMapping
    public Item addItem(@RequestBody Item item){
        return itemService.addItem(item);
    }

    @PutMapping("/{itemId}")
    public Item updateItem(@PathVariable Long itemId, @RequestBody Item item){
        return itemService.updateItem(itemId, item);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItemsByName(@RequestParam String Keyword){
        return itemService.searchItemByName(Keyword);
    }


    @PostMapping("/{itemId}/reviews") // handle POST requests at /items/{itemId}/reviews
    public Review addReview(@PathVariable Long itemId, @RequestBody Review review) {
        return itemService.addReview(itemId, review);
    }

    @GetMapping("/{itemId}/reviews") // handle GET requests at /items/{itemId}/reviews
    public List<Review> getReviewByItemId(@PathVariable Long itemId) {
        return itemService.getReviewByItemId(itemId);
    }

}

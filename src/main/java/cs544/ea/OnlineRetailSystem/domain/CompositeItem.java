package cs544.ea.OnlineRetailSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
public class CompositeItem extends Item{
    //CompositeItem represents a combination of products sold as a single unit.
    // They would be used similarly to Item but could have additional methods
    // or attributes relevant to their specific types
    @OneToMany
    private List<Item> items;

    //here is a simple example how we can use compositeItem
    /*// create an instance of IndividualItem
IndividualItem item1 = new IndividualItem();
item1.setName("Laptop");
item1.setDescription("High performance laptop");
item1.setPrice(1000.0);
item1.setQuantityInStock(10);
item1.setUser(merchantUser); // Assuming merchantUser is the user who owns this item.
itemRepository.save(item1);

IndividualItem item2 = new IndividualItem();
item2.setName("Mouse");
item2.setDescription("Wireless mouse");
item2.setPrice(50.0);
item2.setQuantityInStock(20);
item2.setUser(merchantUser);
itemRepository.save(item2);

// create an instance of CompositeItem
CompositeItem compositeItem = new CompositeItem();
compositeItem.setName("Computer Bundle");
compositeItem.setDescription("High performance laptop with a wireless mouse");
compositeItem.setPrice(1020.0); // discounted price
compositeItem.setQuantityInStock(5); // Let's say we have 5 of these bundles
compositeItem.setUser(merchantUser);

// add individual items to composite item
compositeItem.getItems().add(item1);
compositeItem.getItems().add(item2);

itemRepository.save(compositeItem);

    * */
}

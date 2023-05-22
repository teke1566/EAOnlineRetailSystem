package cs544.ea.OnlineRetailSystem.service.Impl;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
    public List<Item> searchItems(String Keyword) {
        return itemRepository.findByNameContainingIgnoreCase(Keyword);
    }
}

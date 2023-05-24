package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import cs544.ea.OnlineRetailSystem.service.Impl.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        // Create a list of items
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item());
        itemList.add(new Item());

        // Mock the repository behavior
        Mockito.when(itemRepository.findAll()).thenReturn(itemList);

        // Call the service method
        List<Item> result = itemService.getAllItems();

        // Verify the result
        Assertions.assertEquals(2, result.size());
        Mockito.verify(itemRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetItemById() {
        // Create a sample item
        Item item = new Item();
        item.setItemId(1L);
        item.setName("Test Item");

        // Mock the repository behavior
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Call the service method
        Item result = itemService.getItemById(1L);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test Item", result.getName());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testAddItem() {
        // Create a sample item
        Item item = new Item();
        item.setName("Test Item");

        // Mock the repository behavior
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);

        // Call the service method
        Item result = itemService.addItem(item);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test Item", result.getName());
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void testUpdateItem() {
        // Create a sample item
        Item item = new Item();
        item.setItemId(1L);
        item.setName("Existing Item");

        // Create an updated item
        Item updatedItem = new Item();
        updatedItem.setName("Updated Item");

        // Mock the repository behavior
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(updatedItem);

        // Call the service method
        Item result = itemService.updateItem(1L, updatedItem);

        // Verify the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated Item", result.getName());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void testDeleteItem() {
        // Create a sample item
        Item item = new Item();
        item.setItemId(1L);
        item.setName("Test Item");

        // Mock the repository behavior
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Call the service method
        itemService.deleteItem(1L);

        // Verify the delete operation
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).delete(item);
    }

    @Test
    void testSearchItems() {
        // Create a keyword for searching
        String keyword = "Test";

        // Create a list of items
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item());
        itemList.add(new Item());

        // Mock the repository behavior
        Mockito.when(itemRepository.findByNameContainingIgnoreCase(keyword)).thenReturn(itemList);

        // Call the service method
        List<Item> result = itemService.searchItemByName(keyword);

        // Verify the result
        Assertions.assertEquals(2, result.size());
        Mockito.verify(itemRepository, Mockito.times(1)).findByNameContainingIgnoreCase(keyword);
    }


}


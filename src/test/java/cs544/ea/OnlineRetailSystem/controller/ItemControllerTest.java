package cs544.ea.OnlineRetailSystem.controller;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.service.CustomerService;
import cs544.ea.OnlineRetailSystem.service.ItemService;
import cs544.ea.OnlineRetailSystem.service.PublicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CustomerService customerService;
    @Mock
    private ItemService itemService;
    @Mock
    private PublicService publicService;

    private MerchantController merchantController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        merchantController = new MerchantController(itemService, customerService, publicService);
        mockMvc = MockMvcBuilders.standaloneSetup(merchantController).build();
    }


    @Test
    public void testGetItemById() throws Exception {
        // Mocked item
        Item item = new Item();
        item.setItemId(1L);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        // Mocking the behavior of the itemService
        when(itemService.getItemById(1L)).thenReturn(item);

        // Perform the GET request to /api/v1/items/{itemId}
        mockMvc.perform(get("/api/v1/items/{itemId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemId").value(1))
                .andExpect(jsonPath("$.name").value("Item 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(itemService, times(1)).getItemById(1L);
    }

    @Test
    public void testAddItem() throws Exception {
        // Mocked item
        Item item = new Item();
        item.setItemId(1L);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setPrice(10.0);
        // Mocking the behavior of the itemService
        when(itemService.addItem(any(Item.class))).thenReturn(item);

        // Request body JSON
        String requestBody = "{\"name\":\"Item 1\",\"description\":\"Description 1\",\"price\":10.0}";

        // Perform the POST request to /api/v1/items
        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemId").value(1))
                .andExpect(jsonPath("$.name").value("Item 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(itemService, times(1)).addItem(any(Item.class));
    }


    @Test
    public void testDeleteItem() throws Exception {
        // Perform the DELETE request to /api/v1/items/{itemId}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/items/{itemId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(itemService, Mockito.times(1)).deleteItem(1L);
    }

    @Test
    public void testSearchItemsByName() throws Exception {
        // Mocked items
        Item item1 = new Item();
        item1.setItemId(1L);
        item1.setName("Item 1");

        Item item2 = new Item();
        item2.setItemId(2L);
        item2.setName("Item 2");

        List<Item> items = Arrays.asList(item1, item2);

        Mockito.when(itemService.searchItemByName("Item")).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/search")
                        .param("Keyword", "Item"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        Mockito.verify(itemService, Mockito.times(1)).searchItemByName("Item");
    }


}

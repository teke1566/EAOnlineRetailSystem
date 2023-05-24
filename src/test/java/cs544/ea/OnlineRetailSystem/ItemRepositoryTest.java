package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void TestFindItemsByMerchantId() {
        //Arrange
        User merchant = new User();
        entityManager.persistAndFlush(merchant);

        Item item = new Item();
        item.setMerchant(merchant);
        entityManager.persistAndFlush(item);

        //Act
        List<Item> foundItem = itemRepository.findItemsByMerchantId(merchant.getId());
        System.out.println("foundItem: "+foundItem);

        // Assert
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.get(0)).isEqualTo(item);
    }
}

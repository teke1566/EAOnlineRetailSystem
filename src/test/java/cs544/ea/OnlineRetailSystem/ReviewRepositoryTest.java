package cs544.ea.OnlineRetailSystem;

import cs544.ea.OnlineRetailSystem.domain.Item;
import cs544.ea.OnlineRetailSystem.domain.Review;
import cs544.ea.OnlineRetailSystem.domain.User;
import cs544.ea.OnlineRetailSystem.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
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
public class ReviewRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testFindByItemId(){
        //Arrange
        Item item=new Item();
        entityManager.persistAndFlush(item);

        User user= new User();
        entityManager.persistAndFlush(user);

        Review review= new Review();
        review.setItem(item);
        review.setBuyer(user);
        entityManager.persistAndFlush(review);

        //Act

        List<Review> foundReviews= reviewRepository.findByItemId(item.getItemId());

        //Assert

        assertThat(foundReviews).isNotNull();
        assertThat(foundReviews.get(0)).isEqualTo(review);
    }

    @Test
    public void testFindByUserId(){
        //Arrange
        Item item= new Item();
        entityManager.persistAndFlush(item);

        User user = new User();
        entityManager.persistAndFlush(user);

        Review review= new Review();
        review.setItem(item);
        review.setBuyer(user);
        entityManager.persistAndFlush(review);

        //Act
        List<Review> foundReviews= reviewRepository.findByUserId(user.getId());

        //Assert
        assertThat(foundReviews).isNotNull();
        assertThat(foundReviews.get(0)).isEqualTo(review);


    }
}

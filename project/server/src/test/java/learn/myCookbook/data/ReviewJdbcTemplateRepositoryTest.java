package learn.myCookbook.data;

import learn.myCookbook.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReviewJdbcTemplateRepositoryTest {

    @Autowired
    ReviewJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Review> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 4);
    }

    @Test
    void shouldFindById() {
        Review review = repository.findById(1);
        assertNotNull(review);
        assertEquals("Very nice!", review.getComment());
    }

    @Test
    void shouldNotFindBYMissingId() {
        Review missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindByUserId() {
        List<Review> list = repository.findByUserId(1);
        assertNotNull(list);
        assertEquals(3, list.size());
    }

    @Test
    void shouldNotFindByMissingUserId() {
        List<Review> empty = repository.findByUserId(999);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldFindByRecipeId() {
        List<Review> list = repository.findByRecipeId(1);
        assertNotNull(list);
        assertEquals(4, list.get(0).getReviewId());
    }

    @Test
    void shouldFindBYRecipeIdRatingDesc() {
        List<Review> list = repository.findByRecipeIdRatingDesc(1);
        assertNotNull(list);
        assertEquals(1, list.get(0).getReviewId());
    }

    @Test
    void shouldFindBYRecipeIdRatingAsc() {
        List<Review> list = repository.findByRecipeIdRatingAsc(1);
        assertNotNull(list);
        assertEquals(4, list.get(0).getReviewId());
    }

    @Test
    void shouldNotFindBYMissingRecipeId() {
        List<Review> list = repository.findByRecipeId(999);
        assertNotNull(list);
        assertEquals(0, list.size());

        List<Review> desc = repository.findByRecipeIdRatingDesc(999);
        assertNotNull(desc);
        assertEquals(0, desc.size());

        List<Review> asc = repository.findByRecipeIdRatingAsc(999);
        assertNotNull(asc);
        assertEquals(0, asc.size());
    }

    @Test
    void shouldAdd() {
        Review review = new Review();
        review.setReviewId(0);
        review.setRating(5);
        review.setComment("Wonderful!");
        review.setDate(LocalDate.now().minusDays(1));
        review.setUserId(3);
        review.setRecipeId(3);

        Review result = repository.add(review);
        assertNotNull(result);
        assertTrue(result.getReviewId() >= 5);
    }

    @Test
    void shouldUpdate() {
        Review review = new Review();
        review.setReviewId(2);
        review.setRating(4);
        review.setComment("Pretty good");
        review.setDate(LocalDate.of(2020, 11, 11));
        review.setUserId(1);
        review.setRecipeId(2);

        assertTrue(repository.update(review));
        assertEquals("Pretty good", repository.findById(2).getComment());
    }

    @Test
    void shouldNotUpdateMissing() {
        Review review = new Review();
        review.setReviewId(999);
        review.setRating(4);
        review.setComment("Pretty good");
        review.setDate(LocalDate.of(2020, 11, 11));
        review.setUserId(1);
        review.setRecipeId(2);

        assertFalse(repository.update(review));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(5));
        assertFalse(repository.deleteById(5));
    }
}

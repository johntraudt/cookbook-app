package learn.myCookbook.domain;

import learn.myCookbook.data.ReviewRepository;
import learn.myCookbook.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository reviewRepository;

    @Test
    void shouldNotAddWithId() {
       Review review = makeReview();
       review.setReviewId(1);
       Result<Review> result = service.add(review);
       assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidRating() {
        Review review = makeReview();
        review.setRating(0);
        Result<Review> result = service.add(review);
        assertEquals(ResultType.INVALID, result.getType());

        review.setRating(6);
        result = service.add(review);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddDuplicate() {
        
    }

    @Test
    void shouldUpdate() {

    }


    Review makeReview() {
        Review review = new Review();
        review.setRating(4);
        review.setComment("Amazing!");
        review.setRecipeId(1);
        review.setUserId(1);
        return review;
    }

}
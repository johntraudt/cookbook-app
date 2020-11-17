package learn.myCookbook.domain;

import learn.myCookbook.data.ReviewRepository;
import learn.myCookbook.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReviewServiceTest {

    @Autowired
    ReviewService service;

    @MockBean
    ReviewRepository reviewRepository;


    @Test
    void shouldNotAddNull() {
        Review review = null;
        Result<Review> result = service.add(review);
        assertEquals(ResultType.INVALID, result.getType());
    }

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
    void shouldNotAddNullDate() {
        Review review = makeReview();
        review.setDate(null);
        Result<Review> result = service.add(review);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdate() {
        Review review = makeReview();
        review.setReviewId(1);

        when(reviewRepository.update(review)).thenReturn(true);
        Result<Review> actual = service.update(review);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        Review review = makeReview();
        review.setReviewId(200);

        when(reviewRepository.update(review)).thenReturn(false);
        Result<Review> actual = service.update(review);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }


    Review makeReview() {
        Review review = new Review();
        review.setRating(4);
        review.setComment("Amazing!");
        review.setDate(LocalDate.of(2020,11,10));
        review.setRecipeId(1);
        review.setUserId(1);
        return review;
    }

}
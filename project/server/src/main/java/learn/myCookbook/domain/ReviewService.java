package learn.myCookbook.domain;

import learn.myCookbook.data.ReviewRepository;
import learn.myCookbook.models.Ingredient;
import learn.myCookbook.models.Review;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ReviewService {

    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public List<Review> findAll() {
        return repository.findAll();
    }

    public Review findById(int reviewId) {
        return repository.findById(reviewId);
    }

    public List<Review> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public List<Review> findByRecipeId(int recipeId) {
        return repository.findByRecipeId(recipeId);
    }

    public List<Review> findByRecipeIdRatingDesc(int recipeId) {
        return repository.findByRecipeIdRatingDesc(recipeId);
    }

    public List<Review> findByRecipeIdRatingAsc(int recipeId) {
        return repository.findByRecipeIdRatingAsc(recipeId);
    }

    public Result<Review> add(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() != 0) {
            result.addMessage("reviewId cannot be set for `add` operation.", ResultType.INVALID);
            return result;
        }

        review = repository.add(review);
        result.setPayload(review);
        return result;
    }

    public Result<Review> update(Review review) {
        Result<Review> result = validate(review);
        if (!result.isSuccess()) {
            return result;
        }

        if (review.getReviewId() <= 0) {
            result.addMessage("reviewId must be set for `update` operation.", ResultType.INVALID);
            return result;
        }

        if (!repository.update(review)) {
            String msg = String.format("reviewId: %s, not found", review.getReviewId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int reviewId) {
        return repository.deleteById(reviewId);
    }

    private Result<Review> validate(Review review) {
        Result<Review> result = new Result<>();
        if (review == null) {
            result.addMessage("review cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Review>> violations = validator.validate(review);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Review> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }


        return result;
    }

//    //delete by recipe
//    boolean deleteById(int recipeId);

}

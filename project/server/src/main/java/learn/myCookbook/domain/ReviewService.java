package learn.myCookbook.domain;

import learn.myCookbook.data.ReviewJdbcTemplateRepository;
import learn.myCookbook.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewJdbcTemplateRepository repository;

    public ReviewService(ReviewJdbcTemplateRepository repository) {
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
        return repository.findByRecipId(recipeId);
    }

    public List<Review> findByRecipeIdRatingDesc(int recipeId) {
        return repository.findByRecipeIdRatingDesc(recipeId);
    }

    public List<Review> findByRecipeIdRatingAsc(int recipeId) {
        return repository.findByRecipeIdRatingAsc(recipeId);
    }

    /*
        //add
        Review add(Review review);

        //update
        boolean update(Review review);

        //delete by id
        boolean deleteById(int reviewId);

        //delete by recipe
        boolean deleteByRecipeId(int recipeId);
    */
}

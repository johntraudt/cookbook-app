package learn.myCookbook.data;

import learn.myCookbook.models.Review;

import java.util.List;

public interface ReviewRepository {
    List<Review> findAll();

    Review findById(int reviewId);

    //find by user sort by date desc
    List<Review> findByUserId(int userId);

    //find by recipe sort by date desc
    List<Review> findByRecipId(int recipeId);

    //find by recipe sort by low
    List<Review> findByRecipeIdRatingDesc(int recipeId);

    //find by recipe sort by high
    List<Review> findByRecipeIdRatingAsc(int recipeId);

    //add
    Review add(Review review);

    //update
    boolean update(Review review);

    //delete by id
    boolean deleteById(int reviewId);

    //delete by recipe
    boolean deleteByRecipeId(int recipeId);





}

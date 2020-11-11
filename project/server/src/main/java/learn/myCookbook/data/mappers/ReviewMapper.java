package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setRating(resultSet.getInt("rating"));
        if (resultSet.getString("comment") != null) {
            review.setComment(resultSet.getString("comment"));
        }
        review.setUserId(resultSet.getInt("user_id"));
        review.setRecipeId(resultSet.getInt("recipe_id"));

        // TODO bug: no date field in review table
        // review.setDate(resultSet.getDate("date").toLocalDate());

        return review;
    }

}

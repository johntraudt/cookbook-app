package learn.myCookbook.data;

import learn.myCookbook.data.mappers.ReviewMapper;
import learn.myCookbook.models.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReviewJdbcTemplateRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AppUserRepository userRepository;

    public ReviewJdbcTemplateRepository(JdbcTemplate jdbcTemplate, AppUserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> findAll() {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review limit 1000;";

        return jdbcTemplate.query(sql, new ReviewMapper());
    }

    @Override
    public Review findById(int reviewId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where review_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), reviewId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Review> findByUserId(int userId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where user_id = ? " +
                "order by review_date desc;";

        return jdbcTemplate.query(sql, new ReviewMapper(), userId);
    }

    @Override
    public List<Review> findByRecipeId(int recipeId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where recipe_id = ? " +
                "order by review_id desc;";

        List<Review> reviews = jdbcTemplate.query(sql, new ReviewMapper(), recipeId);

        for (Review review : reviews) {
            review.setUser(userRepository.findById(review.getUserId()));
        }

        return reviews;
    }

    @Override
    public List<Review> findByRecipeIdRatingDesc(int recipeId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where recipe_id = ? " +
                "order by rating desc;";

        return jdbcTemplate.query(sql, new ReviewMapper(), recipeId);
    }

    @Override
    public List<Review> findByRecipeIdRatingAsc(int recipeId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where recipe_id = ? " +
                "order by rating asc;";

        return jdbcTemplate.query(sql, new ReviewMapper(), recipeId);
    }

    @Override
    public Review findByUserIdAndRecipeId(int userId, int recipeId) {
        final String sql = "select review_id, rating, comment, review_date, user_id, recipe_id " +
                "from review where user_id = ? " +
                "and recipe_id = ?;";

        return jdbcTemplate.query(sql, new ReviewMapper(), userId, recipeId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Review add(Review review) {
        final String sql = "insert into review " +
                "(rating, comment, review_date, user_id, recipe_id) " +
                "values " +
                "(?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, review.getReviewId());
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setObject(3, review.getDate());
            ps.setInt(4, review.getUserId());
            ps.setInt(5, review.getRecipeId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        review.setReviewId(keyHolder.getKey().intValue());
        return review;
    }

    @Override
    public boolean update(Review review) {
        final String sql = "update review set " +
                "rating = ?, " +
                "comment = ?, " +
                "review_date = ?, " +
                "user_id = ?, " +
                "recipe_id = ? " +
                "where review_id = ?;";

        return jdbcTemplate.update(sql,
                review.getRating(),
                review.getComment(),
                review.getDate(),
                review.getUserId(),
                review.getRecipeId(),
                review.getReviewId()) > 0;
    }

    @Override
    public boolean deleteById(int reviewId) {
        final String sql = "delete from review " +
                "where review_id = ?";

        return jdbcTemplate.update(sql, reviewId) > 0;
    }

    @Override
    public boolean deleteByRecipeId(int recipeId) {
        final String sql = "delete from review " +
                "where recipe_id = ?";

        return jdbcTemplate.update(sql, recipeId) > 0;
    }
}

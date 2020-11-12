package learn.myCookbook.data;

import learn.myCookbook.data.mappers.DirectionMapper;
import learn.myCookbook.data.mappers.RecipeMapper;
import learn.myCookbook.data.mappers.ReviewMapper;
import learn.myCookbook.models.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeJdbcTemplateRepository implements RecipeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserJdbcTemplateRepository userRepository;

    public RecipeJdbcTemplateRepository(JdbcTemplate jdbcTemplate, UserJdbcTemplateRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<Recipe> findAll() {

        final String sql = "select recipe_id, name, prep_time, cook_time, servings, date, was_updated, calories, image_link, user_id " +
                "from recipe limit 1000;";

        return jdbcTemplate.query(sql, new RecipeMapper());
    }

    @Override
    public Recipe findById(int recipeId) {

        final String sql = "select recipe_id, name, prep_time, cook_time, servings, date, was_updated, calories, image_link, user_id " +
                "from recipe " +
                "where recipe_id = ?;";

        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(), recipeId)
                .stream()
                .findFirst().orElse(null);

        if (recipe != null) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            addReviews(recipe);
            addDirections(recipe);
        }

        return recipe;
    }

    private void addDirections(Recipe recipe) {
        final String sql = "select d.direction_id, d.recipe_id, d.direction_number, d.text " +
                "from direction d " +
                "join recipe r on r.recipe_id = d.recipe_id " +
                "where r.recipe_id = ?;";

        var directions = jdbcTemplate.query(sql, new DirectionMapper(), recipe.getRecipeId());
        recipe.setDirections(directions);
    }

    private void addReviews(Recipe recipe) {
        final String sql = "select re.review_id, re.rating, re.comment, re.date, re.user_id, re.recipe_id " +
                "from review re " +
                "join recipe r on r.recipe_id = re.recipe_id " +
                "where r.recipe_id = ?;";

        var reviews = jdbcTemplate.query(sql, new ReviewMapper(), recipe.getRecipeId());
        recipe.setReviews(reviews);
    }
}

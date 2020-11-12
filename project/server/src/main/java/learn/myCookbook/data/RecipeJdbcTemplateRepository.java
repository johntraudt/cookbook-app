package learn.myCookbook.data;

import learn.myCookbook.data.mappers.*;
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

        final String sql = "select recipe_id, name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe limit 1000;";

        return jdbcTemplate.query(sql, new RecipeMapper());
    }

    @Override
    public Recipe findById(int recipeId) {

        final String sql = "select recipe_id, name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe " +
                "where recipe_id = ?;";

        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(), recipeId)
                .stream()
                .findFirst().orElse(null);

        if (recipe != null) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            addReviews(recipe);
            addDirections(recipe);
            addTags(recipe);
            addIngredients(recipe);
        }

        return recipe;
    }

    @Override
    public Recipe add(Recipe recipe) {
        return null;
    }

    @Override
    public boolean update(Recipe recipe) {
        return false;
    }

    @Override
    public boolean deleteById(int recipeId) {
        return false;
    }

    private void addIngredients(Recipe recipe) {
        final String sql = "select ri.recipe_ingredient_id, r.recipe_id, ri.ingredient_id, ri.ingredient_list_index, mu.measurement_unit_id, i.name, mu.name 'mName' " +
                "from recipe_ingredient ri " +
                "join recipe r on r.recipe_id = ri.recipe_id " +
                "join ingredient i on i.ingredient_id = ri.ingredient_id " +
                "left join measurement_unit mu on mu.measurement_unit_id = ri.measurement_unit_id " +
                "where r.recipe_id = ?;";

        var ingredients = jdbcTemplate.query(sql, new RecipeIngredientMapper(), recipe.getRecipeId());
        recipe.setIngredients(ingredients);
    }

    private void addTags(Recipe recipe) {
        final String sql = "select rt.recipe_tag_id, rt.name, rt.recipe_tag_category_id " +
                "from recipe_tag rt " +
                "join recipe_recipe_tag rrt on rrt.recipe_tag_id = rt.recipe_tag_id " +
                "join recipe r on r.recipe_id = rrt.recipe_id " +
                "where r.recipe_id = ?;";

        var tags = jdbcTemplate.query(sql, new RecipeTagMapper(), recipe.getRecipeId());
        recipe.setTags(tags);
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

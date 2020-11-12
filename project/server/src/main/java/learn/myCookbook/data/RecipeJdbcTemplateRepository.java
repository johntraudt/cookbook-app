package learn.myCookbook.data;

import learn.myCookbook.data.mappers.RecipeMapper;
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

        recipe.setUser(userRepository.findById(recipe.getUserId()));

        return recipe;
    }
}

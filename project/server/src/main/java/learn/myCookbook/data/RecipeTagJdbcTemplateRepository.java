package learn.myCookbook.data;

import learn.myCookbook.data.mappers.RecipeTagMapper;
import learn.myCookbook.models.RecipeTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeTagJdbcTemplateRepository implements RecipeTagRepository{

    private final JdbcTemplate jdbcTemplate;
    private final RecipeRepository recipeRepository;

    public RecipeTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate, RecipeRepository recipeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeTag> findAll() {
        final String sql = "select recipe_tag_id, recipe_tag_name, recipe_tag_category_id, tag_image_link " +
                "from recipe_tag " +
                "order by recipe_tag_name asc;";

        return jdbcTemplate.query(sql, new RecipeTagMapper());
    }

    @Override
    public RecipeTag findById(int recipeTagId) {
        final String sql = "select recipe_tag_id, recipe_tag_name, recipe_tag_category_id, tag_image_link " +
                "from recipe_tag " +
                "where recipe_tag_id = ?;";

        RecipeTag recipeTag = jdbcTemplate.query(sql, new RecipeTagMapper(), recipeTagId)
                .stream()
                .findFirst()
                .orElse(null);

        if (recipeTag != null) {
            recipeTag.setRecipes(recipeRepository.findByRecipeTagId(recipeTagId));
        }

        return recipeTag;
    }

    @Override
    public RecipeTag findByName(String name) {
        return null;
    }

    @Override
    public RecipeTag add(RecipeTag recipeTag) {
        return null;
    }

    @Override
    public boolean update(RecipeTag recipeTag) {
        return false;
    }

    @Override
    public boolean tagRecipe(int recipeId, int recipeTagId) {
        final String sql = "insert into recipe_recipe_tag " +
                "(recipe_id, recipe_tag_id) " +
                "values " +
                "(?, ?);";

        return jdbcTemplate.update(sql, recipeId, recipeTagId) > 0;
    }

    @Override
    public boolean recipeAlreadyTagged(int recipeId, int recipeTagId) {
        final String sql = "select rt.recipe_tag_id, rt.recipe_tag_name, rt.recipe_tag_category_id, rt.tag_image_link " +
                "from recipe_tag rt " +
                "join recipe_recipe_tag rrt on rt.recipe_tag_id = rrt.recipe_tag_id " +
                "join recipe r on rrt.recipe_id = r.recipe_id " +
                "where r.recipe_id = ? " +
                "and rt.recipe_tag_id = ?;";

        return jdbcTemplate.query(sql, new RecipeTagMapper(), recipeId, recipeTagId).size() > 0;
    }

    @Override
    public boolean deleteById(int recipeTagId) {
        return false;
    }
}

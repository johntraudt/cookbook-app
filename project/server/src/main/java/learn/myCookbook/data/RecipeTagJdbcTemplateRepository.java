package learn.myCookbook.data;

import learn.myCookbook.data.mappers.RecipeTagMapper;
import learn.myCookbook.models.RecipeTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeTagJdbcTemplateRepository implements RecipeTagRepository{

    private final JdbcTemplate jdbcTemplate;

    public RecipeTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeTag> findAll() {
        final String sql = "select recipe_tag_id, recipe_tag_name, recipe_tag_category_id, tag_image_link " +
                "from recipe_tag " +
                "order by recipe_tag_id limit 1000;";

        return jdbcTemplate.query(sql, new RecipeTagMapper());
    }

    @Override
    public RecipeTag findById(int recipeTagId) {
        return null;
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
    public boolean deleteById(int recipeTagId) {
        return false;
    }
}

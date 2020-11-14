package learn.myCookbook.data;

import learn.myCookbook.data.mappers.RecipeTagCategoryMapper;
import learn.myCookbook.models.RecipeTagCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeTagCategoryJdbcTemplateRepository  implements RecipeTagCategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecipeTagCategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeTagCategory> findAll() {
        final String sql = "select recipe_tag_category_id, recipe_tag_category_name " +
                "from recipe_tag_category " +
                "order by recipe_tag_category_id limit 1000;";

        return jdbcTemplate.query(sql, new RecipeTagCategoryMapper());
    }

    @Override
    public RecipeTagCategory findById() {
        return null;
    }

    @Override
    public RecipeTagCategory findByName() {
        return null;
    }

    @Override
    public RecipeTagCategory add(RecipeTagCategory recipeTagCategory) {
        return null;
    }

    @Override
    public boolean update(RecipeTagCategory recipeTagCategory) {
        return false;
    }

    @Override
    public boolean deleteById(int recipeTageCategoryId) {
        return false;
    }
}

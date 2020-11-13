package learn.myCookbook.data.mappers;

import learn.myCookbook.models.RecipeTagCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeTagCategoryMapper implements RowMapper<RecipeTagCategory> {

    @Override
    public RecipeTagCategory mapRow(ResultSet resultSet, int i) throws SQLException {
        RecipeTagCategory recipeTagCategory = new RecipeTagCategory();
        recipeTagCategory.setRecipeTagCategoryId(resultSet.getInt("recipe_tag_category_id"));
        recipeTagCategory.setName(resultSet.getString("recipe_tag_category_name"));
        return recipeTagCategory;
    }
}

package learn.myCookbook.data.mappers;

import learn.myCookbook.models.RecipeTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeTagMapper implements RowMapper<RecipeTag> {

    @Override
    public RecipeTag mapRow(ResultSet resultSet, int i) throws SQLException {
        RecipeTag recipeTag = new RecipeTag();
        recipeTag.setRecipeTagId(resultSet.getInt("recipe_tag_id"));
        recipeTag.setName(resultSet.getString("recipe_tag_name"));
        recipeTag.setImageLink(resultSet.getString("tag_image_link"));

        if (resultSet.getInt("recipe_tag_category_id") != 0) {
            recipeTag.setRecipeTagCategoryId(resultSet.getInt("recipe_tag_category_id"));
        }

        return recipeTag;
    }
}

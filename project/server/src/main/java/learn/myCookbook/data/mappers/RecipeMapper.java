package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeMapper implements RowMapper<Recipe> {

    @Override
    public Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
        Recipe recipe= new Recipe();
        recipe.setRecipeId(resultSet.getInt("recipe_id"));
        recipe.setUserId(resultSet.getInt("user_id"));
        recipe.setName(resultSet.getString("recipe_name"));
        recipe.setPrepTimeInMinutes(resultSet.getInt("prep_time"));
        recipe.setCookTimeInMinutes(resultSet.getInt("cook_time"));
        recipe.setServings(resultSet.getInt("servings"));
        recipe.setDate(resultSet.getDate("date").toLocalDate());
        recipe.setWasUpdated(resultSet.getBoolean("was_updated"));
        recipe.setFeatured(resultSet.getBoolean("is_featured"));
        recipe.setImageLink(resultSet.getString("image_link"));

        return recipe;
    }
}

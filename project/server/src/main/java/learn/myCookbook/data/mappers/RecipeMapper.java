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

        recipe.setName(resultSet.getString("name"));
        recipe.setPrepTimeInMinutes(resultSet.getInt("prep_time"));
        recipe.setCookTimeInMinutes(resultSet.getInt("cook_time"));
        recipe.setServings(resultSet.getInt("servings"));
        recipe.setDate(resultSet.getDate("date").toLocalDate());
        recipe.setWasUpdated(resultSet.getBoolean("was_updated"));
        recipe.setImageLink(resultSet.getString("image_link"));

//        UserMapper userMapper = new UserMapper();
//        recipe.setUser(userMapper.mapRow(resultSet, i));


        return recipe;
    }
}

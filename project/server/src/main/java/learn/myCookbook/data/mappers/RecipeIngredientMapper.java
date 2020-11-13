package learn.myCookbook.data.mappers;

import learn.myCookbook.models.RecipeIngredient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeIngredientMapper implements RowMapper<RecipeIngredient> {

    @Override
    public RecipeIngredient mapRow(ResultSet resultSet, int i) throws SQLException {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeIngredientId(resultSet.getInt("recipe_ingredient_id"));
        recipeIngredient.setRecipeId(resultSet.getInt("recipe_id"));
        recipeIngredient.setQuantity(resultSet.getString("quantity"));
        recipeIngredient.setIngredientId(resultSet.getInt("ingredient_id"));
        recipeIngredient.setIngredientListIndex(resultSet.getInt("ingredient_list_index"));
        recipeIngredient.setMeasurementUnitId(resultSet.getInt("measurement_unit_id"));

        IngredientMapper ingredientMapper = new IngredientMapper();
        recipeIngredient.setIngredient(ingredientMapper.mapRow(resultSet, i));

        MeasurementUnitMapper measurementUnitMapper = new MeasurementUnitMapper();
        recipeIngredient.setMeasurementUnit(measurementUnitMapper.mapRow(resultSet, i));

        return recipeIngredient;
    }


}

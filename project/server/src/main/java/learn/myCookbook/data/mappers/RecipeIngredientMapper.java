package learn.myCookbook.data.mappers;

import learn.myCookbook.models.RecipeIngredient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeIngredientMapper implements RowMapper<RecipeIngredient> {

    @Override
    public RecipeIngredient mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

}

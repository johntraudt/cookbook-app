package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Ingredient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientMapper implements RowMapper<Ingredient> {

    @Override
    public Ingredient mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

}

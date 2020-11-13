package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Direction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectionMapper implements RowMapper<Direction> {

    @Override
    public Direction mapRow(ResultSet resultSet, int i) throws SQLException {
        Direction direction = new Direction();
        direction.setDirectionId(resultSet.getInt("direction_id"));
        direction.setRecipeId(resultSet.getInt("recipe_id"));
        direction.setDirectionNumber(resultSet.getInt("direction_number"));
        direction.setText(resultSet.getString("direction_text"));
        return direction;
    }
}

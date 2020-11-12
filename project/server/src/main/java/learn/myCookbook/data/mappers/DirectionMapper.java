package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Direction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectionMapper implements RowMapper<Direction> {

    @Override
    public Direction mapRow(ResultSet resultSet, int i) throws SQLException {
        Direction direction = new Direction();
        direction.setDirection_id(resultSet.getInt("direction_id"));
        direction.setRecipe_id(resultSet.getInt("recipe_id"));
        direction.setDirection_number(resultSet.getInt("direction_number"));
        direction.setText(resultSet.getString("text"));
        return direction;
    }
}

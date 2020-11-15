package learn.myCookbook.data;

import learn.myCookbook.data.mappers.DirectionMapper;
import learn.myCookbook.models.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DirectionJdbcTemplateRepository implements DirectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public DirectionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Direction findById(int directionId) {
        final String sql = "select direction_id, recipe_id, direction_number, direction_text " +
                "from direction " +
                "where direction_id = ?;";

        return jdbcTemplate.query(sql, new DirectionMapper(), directionId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public Direction findByRecipeIdAndDirectionNumber(int recipeId, int directionNumber) {
        final String sql = "select direction_id, recipe_id, direction_number, direction_text " +
                "from direction " +
                "where recipe_id = ? " +
                "and direction_number = ?;";

        return jdbcTemplate.query(sql, new DirectionMapper(), recipeId, directionNumber)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Direction> findByRecipeId(int recipeId) {
        final String sql = "select direction_id, recipe_id, direction_number, direction_text " +
                "from direction " +
                "where recipe_id = ?;";

        return jdbcTemplate.query(sql, new DirectionMapper(), recipeId);
    }

    @Override
    public Direction add(Direction direction) {

        final String sql = "insert into direction (recipe_id, direction_number, direction_text) " +
                "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, direction.getRecipeId());
            ps.setInt(2, direction.getDirectionNumber());
            ps.setString(3, direction.getText());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        direction.setDirectionId(keyHolder.getKey().intValue());
        return direction;
    }

    @Override
    public boolean update(Direction direction) {
        final String sql = "update direction set " +
                "direction_text = ? " +
                "where direction_id = ?;";

        return jdbcTemplate.update(sql,
                direction.getText(),
                direction.getDirectionId()) > 0;
    }

    @Override
    public boolean decrementDirectionNumber(Direction direction) {
        final String sql = "update direction set " +
                "direction_number = ? " +
                "where direction_id = ?;";

        return jdbcTemplate.update(sql,
                direction.getDirectionNumber() - 1,
                direction.getDirectionId()) > 0;
    }

    @Override
    public boolean deleteById(int directionId) {
        return jdbcTemplate.update("delete from direction where direction_id = ?;", directionId) > 0;
    }
}

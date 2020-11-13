package learn.myCookbook.data;

import learn.myCookbook.data.mappers.DirectionMapper;
import learn.myCookbook.models.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DirectionJdbcTemplateRepository implements DirectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public DirectionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Direction findByRecipeId(int recipeId) {
        final String sql = "select direction_id, recipe_id, direction_number, text " +
                "from direction " +
                "where recipe_id = ?;";

        return jdbcTemplate.query(sql, new DirectionMapper(), recipeId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public Direction add(Direction direction) {
        return null;
    }

    @Override
    public boolean update(Direction direction) {
        return false;
    }

    @Override
    public boolean deleteById(int directionId) {
        return false;
    }
}

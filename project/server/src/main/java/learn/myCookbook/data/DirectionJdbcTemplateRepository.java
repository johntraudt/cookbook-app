package learn.myCookbook.data;

import learn.myCookbook.models.Direction;
import org.springframework.stereotype.Repository;

@Repository
public class DirectionJdbcTemplateRepository implements DirectionRepository {

    @Override
    public Direction findByRecipeId(int recipeId) {
        return null;
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

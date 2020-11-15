package learn.myCookbook.data;

import learn.myCookbook.models.Direction;

import java.util.List;

public interface DirectionRepository {

    Direction findById(int directionId);

    Direction findByRecipeIdAndDirectionNumber(int recipeId, int directionNumber);

    List<Direction> findByRecipeId(int recipeId);

    Direction add(Direction direction);

    boolean update(Direction direction);

    boolean decrementDirectionNumber(Direction direction);

    boolean deleteById(int directionId);
}

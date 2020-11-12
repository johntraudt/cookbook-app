package learn.myCookbook.data;

import learn.myCookbook.models.Direction;

public interface DirectionRepository {

    Direction findByRecipeId(int recipeId);

    Direction add(Direction direction);

    boolean update(Direction direction);

    boolean deleteById(int directionId);
}

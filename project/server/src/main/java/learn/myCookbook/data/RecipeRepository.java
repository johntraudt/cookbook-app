package learn.myCookbook.data;

import learn.myCookbook.models.Recipe;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAll();

    Recipe findById(int recipeId);
}

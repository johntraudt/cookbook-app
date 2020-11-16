package learn.myCookbook.data;

import learn.myCookbook.models.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAll();

    Recipe findById(int recipeId);

    List<Recipe> findByName(String recipeName);

    Recipe findByUserIdAndName(int userId, String name);

    List<Recipe> findByCookbookId(int cookBookId);

    List<Recipe> findByRecipeTagId(int recipeTagId);

    int findRandomRecipeId();

    Recipe add(Recipe recipe);

    boolean update(Recipe recipe);

    @Transactional
    boolean deleteById(int recipeId);

}

package learn.myCookbook.data;

import learn.myCookbook.models.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAll();

    Recipe findById(int recipeId);

    List<Recipe> findByName(String recipeName);

    Recipe add(Recipe recipe);

    boolean update(Recipe recipe);

    @Transactional
    boolean deleteById(int recipeId);

}

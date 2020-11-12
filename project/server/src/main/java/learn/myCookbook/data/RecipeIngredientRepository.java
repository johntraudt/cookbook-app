package learn.myCookbook.data;

import learn.myCookbook.models.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientRepository {
    List<RecipeIngredient> findAll();

    List<RecipeIngredient> findByRecipeId(int recipeId);

    RecipeIngredient findById(int recipeIngredientId);

    RecipeIngredient findByRecipeIdAndIndex(int recipeId, int ingredientListIndex);

    RecipeIngredient add(RecipeIngredient recipeIngredient);

    boolean update(RecipeIngredient recipeIngredient);

    boolean deleteById(int recipeIngredientId);

    boolean deleteByRecipeId(int recipeId);

    boolean deleteByRecipeIdAndIndex(int recipeId, int IngredientListIndex);
}

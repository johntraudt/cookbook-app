package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeIngredientRepository;
import learn.myCookbook.models.RecipeIngredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository repository;

    public RecipeIngredientService(RecipeIngredientRepository repository) {
        this.repository = repository;
    }

    public List<RecipeIngredient> findAll() {
        return repository.findAll();
    }

    public List<RecipeIngredient> findByRecipeId(int recipeId) {
        return repository.findByRecipeId(recipeId);
    }

    public RecipeIngredient findById(int recipeIngredientId) {
        return repository.findById(recipeIngredientId);
    }

    public RecipeIngredient findByRecipeIdAndIndex(int recipeId, int index) {
        return repository.findByRecipeIdAndIndex(recipeId, index);
    }

    /*
        RecipeIngredient add(RecipeIngredient recipeIngredient);

        boolean update(RecipeIngredient recipeIngredient);

        boolean deleteById(int recipeIngredientId);

        boolean deleteByRecipeId(int recipeId);

        boolean deleteByRecipeIdAndIndex(int recipeId, int IngredientListIndex);
    */

}



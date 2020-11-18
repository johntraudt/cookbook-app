package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.*;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    private final RecipeIngredientService recipeIngredientService;
    private final DirectionService directionService;
    private final RecipeTagService recipeTagService;

    public RecipeService(RecipeRepository repository, RecipeIngredientService recipeIngredientService, DirectionService directionService, RecipeTagService recipeTagService) {
        this.repository = repository;
        this.recipeIngredientService = recipeIngredientService;
        this.directionService = directionService;
        this.recipeTagService = recipeTagService;
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public List<Recipe> findFeatured() {
        return repository.findFeatured();
    }

    public Recipe findById(int recipeId) {
        return repository.findById(recipeId);
    }

    public List<Recipe> findByName(String recipeName) {
        return repository.findByName(recipeName);
    }

    public List<Recipe> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public int findRandomRecipeId(){
        return repository.findRandomRecipeId();
    }

    public Result<Recipe> add(Recipe recipe) {
        Result<Recipe> result = validate(recipe);

        if (!result.isSuccess()) {
            return result;
        }

        if (recipe.getRecipeId() != 0) {
            result.addMessage("recipeId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }
        if (repository.findByUserIdAndName(recipe.getUserId(), recipe.getName()) != null) {
            result.addMessage("You already have a recipe with that title.", ResultType.INVALID);
            return result;
        }

        recipe.setRecipeId(repository.add(recipe).getRecipeId());

        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            recipeIngredient.setRecipeId(recipe.getRecipeId());

            Result<RecipeIngredient> recipeIngredientResult = recipeIngredientService.add(recipeIngredient);
            if (!recipeIngredientResult.isSuccess()) {
                for (String message : recipeIngredientResult.getMessages()) {
                    result.addMessage(message, ResultType.INVALID);
                }
                repository.deleteById(recipe.getRecipeId());
                return result;
            }
        }
        for (Direction direction : recipe.getDirections()) {
            direction.setRecipeId(recipe.getRecipeId());

            Result<Direction> directionResult = directionService.add(direction);
            if (!directionResult.isSuccess()) {
                for (String message : directionResult.getMessages()) {
                    result.addMessage(message, ResultType.INVALID);
                }
                repository.deleteById(recipe.getRecipeId());
                return result;
            }
        }
        for (RecipeTag recipeTag : recipe.getTags()) {
            recipeTagService.tagRecipe(recipe.getRecipeId(), recipeTag.getRecipeTagId());
        }

        return result;
    }

    public Result<Recipe> update(Recipe recipe) {
        Result<Recipe> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Recipe> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (recipe.getRecipeId() <= 0) {
            result.addMessage("recipeId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(recipe)) {
            String msg = String.format("recipeId: %s, not found", recipe.getRecipeId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int recipeId) {
        return repository.deleteById(recipeId);
    }

    private Result<Recipe> validate(Recipe recipe) {
        Result<Recipe> result = new Result<>();
        if (recipe == null) {
            result.addMessage("recipe cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Recipe> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}

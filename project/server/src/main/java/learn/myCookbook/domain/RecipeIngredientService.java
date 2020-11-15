package learn.myCookbook.domain;

import learn.myCookbook.data.IngredientRepository;
import learn.myCookbook.data.RecipeIngredientRepository;
import learn.myCookbook.models.Ingredient;
import learn.myCookbook.models.RecipeIngredient;
import learn.myCookbook.models.User;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository repository;
    private final IngredientService ingredientService;

    public RecipeIngredientService(RecipeIngredientRepository repository, IngredientService ingredientService) {
        this.repository = repository;
        this.ingredientService = ingredientService;
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

    public Result<RecipeIngredient> add(RecipeIngredient recipeIngredient) {
        Result result = validate(recipeIngredient);

        if (!result.isSuccess()) {
            return result;
        }

        if (recipeIngredient.getRecipeIngredientId() != 0) {
            result.addMessage("RecipeIngredientId cannot be set for add operation", ResultType.INVALID);
            return result;
        }

        if(recipeIngredient.getMeasurementUnitId() <= 0) {
            result.addMessage("Measurement unit ID must be set. Use 5 for no measurement unit", ResultType.INVALID);
        }

        Result<Ingredient> ingredientResult = ingredientService.add(recipeIngredient.getIngredient());
        if (ingredientResult.isSuccess()) {
            recipeIngredient.setIngredientId(ingredientResult.getPayload().getIngredientId());
        } else{
            for (String message : ingredientResult.getMessages()) {
                result.addMessage(message, ResultType.INVALID);
            }
            return result;
        }

        result.setPayload(repository.add(recipeIngredient));

        return result;
    }

    private Result<RecipeIngredient> validate(RecipeIngredient recipeIngredient) {
        Result<RecipeIngredient> result = new Result<>();
        if (recipeIngredient == null) {
            result.addMessage("RecipeIngredient cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RecipeIngredient>> violations = validator.validate(recipeIngredient);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RecipeIngredient> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        return result;
    }

    /*
        RecipeIngredient add(RecipeIngredient recipeIngredient); <==call add ingredient from here

        boolean update(RecipeIngredient recipeIngredient);

        boolean deleteById(int recipeIngredientId);

        boolean deleteByRecipeId(int recipeId);

        boolean deleteByRecipeIdAndIndex(int recipeId, int IngredientListIndex);
    */

}



package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.Recipe;
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

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public Recipe findById(int recipeId) {
        return repository.findById(recipeId);
    }

    public Result<Recipe> add(Recipe recipe) {
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

        result.setPayload(repository.add(recipe));
        return result;
    }

//    private Result<Recipe> validate(Recipe recipe) {
//        Result<Recipe> result = new Result<>();
//        if (recipe == null) {
//            result.addMessage("recipe canot be null.", ResultType.INVALID);
//            return result;
//        }
//
//
//
//        return result;
//    }
}

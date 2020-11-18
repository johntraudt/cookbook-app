package learn.myCookbook.domain;

import learn.myCookbook.data.IngredientRepository;
import learn.myCookbook.models.Ingredient;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> findAll() {
        return repository.findAll();
    }

    public Ingredient findById(int ingredientId) {
        return repository.findById(ingredientId);
    }

    public Ingredient findByName(String name) {
        return repository.findByName(name);
    }


    public Result<Ingredient> add(Ingredient ingredient) {
        Result<Ingredient> result = validate(ingredient);
        if (!result.isSuccess()) {
            return result;
        }

        if (ingredient.getIngredientId() != 0) {
            result.addMessage("Ingredient ID cannot be set for add operation", ResultType.INVALID);
            return result;
        }

        if (findByName(ingredient.getName()) == null) {
            ingredient = repository.add(ingredient);
            result.setPayload(ingredient);
            return result;
        }

        result.setPayload(repository.findByName(ingredient.getName()));
        return result;
    }

    private Result<Ingredient> validate(Ingredient ingredient) {
        Result<Ingredient> result = new Result<>();
        if (ingredient == null) {
            result.addMessage("Ingredient cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Ingredient> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        return result;
    }


}

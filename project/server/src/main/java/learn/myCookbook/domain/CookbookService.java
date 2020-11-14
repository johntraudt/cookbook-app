package learn.myCookbook.domain;

import learn.myCookbook.data.CookbookRepository;
import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.data.UserRepository;
import learn.myCookbook.models.Cookbook;
import learn.myCookbook.models.Recipe;
import learn.myCookbook.models.User;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class CookbookService {
    private final CookbookRepository repository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public CookbookService(CookbookRepository repository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Cookbook> findAll() {
        return repository.findAll();
    }

    public Cookbook findById(int cookbookId) {
        return repository.findById(cookbookId);
    }

    public List<Cookbook> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Cookbook> findPublicByUserId(int userId) {
        return repository.findPublicByUserId(userId);
    }

    public List<Cookbook> findAllByRecipeId(int recipeId) {
        return repository.findAllByRecipeId(recipeId);
    }

    public List<Cookbook> findPublicByRecipeId(int recipeId) {
        return repository.findPublicByRecipeId(recipeId);
    }

    public List<Cookbook> findPublicByTitle(String title) {
        return repository.findPublicByTitle(title);
    }

    public List<Recipe> findRecipesByCookBookId(int cookbookId) {
        return repository.findRecipesByCookBookId(cookbookId);
    }

    public Result<Cookbook> add(Cookbook cookbook) {
        Result<Cookbook> result = validate(cookbook);

        if (!result.isSuccess()) {
            return result;
        }

        if (repository.titleTakenForUser(cookbook.getUserId(), cookbook.getTitle())) {
            result.addMessage("You've already used that title.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        cookbook = repository.add(cookbook);
        result.setPayload(cookbook);
        return result;
    }

    public boolean insertRecipeById(int cookbookId, int recipeId) {
        if (repository.findById(cookbookId) == null ||
                recipeRepository.findById(recipeId) == null ||
                repository.recipeIsInCookbook(cookbookId, recipeId)) {

            return false;
        }

        return repository.insertRecipeById(cookbookId, recipeId);
    }

    public Result<Cookbook> update(Cookbook cookbook) {
        Result<Cookbook> result = validate(cookbook);

        if (!result.isSuccess()) {
            return result;
        }

        if (cookbook.getCookbookId() <= 0) {
            result.addMessage("Cookbook ID must be set for update", ResultType.INVALID);
            return result;
        }

        result = checkDuplicateTitleOnUpdate(cookbook);

        if (!repository.update(cookbook)) {
            result.addMessage("Could not find that cookbook.", ResultType.INVALID);
        }

        return result;
    }

    public boolean removeRecipeById(int cookbookId, int recipeId) {
        if (repository.findById(cookbookId) == null ||
                recipeRepository.findById(recipeId) == null ||
                !repository.recipeIsInCookbook(cookbookId, recipeId)) {

            return false;
        }

        return repository.removeRecipeById(cookbookId, recipeId);
    }

    public boolean deleteById(int cookbookId) {
        return repository.deleteById(cookbookId);
    }

    //helper methods

    private Result<Cookbook> validate(Cookbook cookbook) {
        Result<Cookbook> result = new Result<>();
        if (cookbook == null) {
            result.addMessage("Cookbook cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Cookbook>> violations = validator.validate(cookbook);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Cookbook> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        if (userRepository.findById(cookbook.getUserId()) == null) {
            result.addMessage("Could not find that user.", ResultType.INVALID);
        }

        return result;
    }

    private Result<Cookbook> checkDuplicateTitleOnUpdate(Cookbook cookbook) {
        Result<Cookbook> result = new Result<>();

        String email = repository.findById(user.getUserId()).getEmail();

        if (!repository.setTitle(cookbook.getCookbookId(), "RESERVED")) {
            String message =  String.format("Cookbook ID: %s not found.", cookbook.getCookbookId());
            result.addMessage(message, ResultType.INVALID);
            return result;
        }

        if (repository.findBy(user.getEmail()) != null) {
            result.addMessage("That email is already taken.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            repository.setUserNameEmail(user.getUserId(), email, userName);
        }

        return result;
    }
}
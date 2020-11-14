package learn.myCookbook.domain;

import learn.myCookbook.data.CookbookRepository;
import learn.myCookbook.data.UserRepository;
import learn.myCookbook.models.Cookbook;
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

    public CookbookService(CookbookRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

    public Result<Cookbook> add(Cookbook cookbook) {
        Result<Cookbook> result = validate(cookbook);

        if (!result.isSuccess()) {
            return result;
        }

        if (repository.titleTakenForUser(cookbook.getUserId(), cookbook.getTitle())) {
            result.addMessage("You've already used that title.", ResultType.INVALID);
        }

        if (userRepository.findById(cookbook.getUserId()) == null) {
            result.addMessage("Could not find that user.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        cookbook = repository.add(cookbook);
        result.setPayload(cookbook);
        return result;
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
            return result;
        }

        return result;
    }
}
    /*
    Cookbook add(Cookbook cookbook);

    boolean update(Cookbook cookbook);

    boolean deleteById(int cookbookId);
     */
package learn.myCookbook.domain;

import learn.myCookbook.data.DirectionRepository;
import learn.myCookbook.models.Cookbook;
import learn.myCookbook.models.Direction;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class DirectionService {

    private final DirectionRepository repository;

    public DirectionService(DirectionRepository repository) {
        this.repository = repository;
    }

    public Direction findById(int directionId) {
        return repository.findById(directionId);
    }

    public Direction findByRecipeIdAndDirectionNumber(int recipeId, int directionNumber) {
        return repository.findByRecipeIdAndDirectionNumber(recipeId, directionNumber);
    }

    public List<Direction> findByRecipeId(int recipeId) {
        return repository.findByRecipeId(recipeId);
    }

    public Result<Direction> add(Direction direction) {
        Result<Direction> result = validate(direction);

        if (!result.isSuccess()) {
            return result;
        }

        if (repository.findByRecipeIdAndDirectionNumber(direction.getRecipeId(), direction.getDirectionNumber()) != null) {
            result.addMessage("This recipe already has a step " + direction.getDirectionNumber(), ResultType.INVALID);
            return result;
        }

        direction= repository.add(direction);
        result.setPayload(direction);
        return result;
    }

    public Result<Direction> update(Direction direction) {
        Result<Direction> result = new Result<>();

        if (!result.isSuccess()) {
            return result;
        }

        if (!repository.update(direction)) {
            result.addMessage("Could not find this step.", ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Direction> decrementDirectionNumber(Direction direction) {
        Result<Direction> result = new Result<>();

        if (!result.isSuccess()) {
            return result;
        }

        if (!repository.decrementDirectionNumber(direction)) {
            String message = String.format("Could not lower step number from %s to %s.", direction.getDirectionNumber(), direction.getDirectionNumber() -1);
            result.addMessage(message, ResultType.INVALID);
        }

        direction = repository.findById(direction.getDirectionId());
        result.setPayload(direction);
        return result;
    }

    public boolean deleteById(int directionId) {
        return repository.deleteById(directionId);
    }

    private Result<Direction> validate(Direction direction) {
        Result<Direction> result = new Result<>();
        if (direction == null) {
            result.addMessage("Direction cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Direction>> violations = validator.validate(direction);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Direction> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        return result;
    }
}

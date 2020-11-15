package learn.myCookbook.domain;

import learn.myCookbook.data.UserRepository;
import learn.myCookbook.models.User;
import learn.myCookbook.models.UserRole;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int userId) {
        return repository.findById(userId);
    }

    public boolean correctUserNamePassword(String userName, String passwordHash) {
        return repository.correctUserNamePassword(userName, passwordHash);
    }

    public Result<User> add(User user) {

        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            result.addMessage("That email is already taken.", ResultType.INVALID);
        }

        if (repository.findByUserName(user.getUserName()) != null) {
            result.addMessage("That username is already taken.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        user = repository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> update(User user) {

        Result<User> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addMessage("User ID must be set for `update` operation.", ResultType.INVALID);
            return result;
        }

        result = checkDuplicateUserNameEmailOnUpdate(user);

        if (!result.isSuccess()) {
            return result;
        }

        repository.update(user);

        return result;
    }

    public boolean deactivateById(int userId) {
        return repository.deactivateById(userId);
    }

    //Helper methods

    private Result<User> validate(User user) {

        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

       return result;
    }

    private Result<User> checkDuplicateUserNameEmailOnUpdate(User user) {
        Result<User> result = new Result<>();

        String email = repository.findById(user.getUserId()).getEmail();
        String userName = repository.findById(user.getUserId()).getUserName();

        if (!repository.setUserNameEmail(user.getUserId(), "RESERVED", "RESERVED")) {
            String message =  String.format("User ID: %s not found.", user.getUserId());
            result.addMessage(message, ResultType.NOT_FOUND);
            return result;
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            result.addMessage("That email is already taken.", ResultType.INVALID);
        }

        if (repository.findByUserName(user.getUserName()) != null) {
            result.addMessage("That username is already taken.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            repository.setUserNameEmail(user.getUserId(), email, userName);
        }

        return result;
    }
}

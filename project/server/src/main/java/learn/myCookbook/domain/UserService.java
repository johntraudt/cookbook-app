package learn.myCookbook.domain;

import learn.myCookbook.data.UserRepository;
import learn.myCookbook.models.User;
import learn.myCookbook.models.UserRole;
import org.springframework.stereotype.Service;

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

    public boolean correctUserNamePassword(String userName, String password) {
        return repository.correctUserNamePassword(userName, password);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for `add` operation.", ResultType.INVALID);
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            result.addMessage("That email is already taken", ResultType.INVALID);
        }

        if (repository.findByUserName(user.getUserName()) != null) {
            result.addMessage("That username is already taken", ResultType.INVALID);
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
            result.addMessage("userId must be set for `update` operation.", ResultType.INVALID);
            return result;
        }

        if (!repository.update(user)) {
            String message =  String.format("userId: %s, not found.", user.getUserId());
            result.addMessage(message, ResultType.INVALID);
        }

        return result;
    }



    private Result<User> validate(User user) {
        // TODO: userRoleId
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("user cannot be null.", ResultType.INVALID);
            return result;
        }

        if (user.getUserRoleId() < 1 || user.getUserRoleId() > UserRole.NUMBER_OF_USER_ROLES) {
            result.addMessage("invalid user role", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getFirstName())) {
            result.addMessage("firstName is required.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getLastName())) {
            result.addMessage("lastName is required.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required.", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getUserName())) {
            result.addMessage("username is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(user.getPasswordHash())) {
            result.addMessage("password is required", ResultType.INVALID);
        }
        return result;
    }

    public boolean deactivateById(int userId) {
        return repository.deactivateById(userId);
    }
}

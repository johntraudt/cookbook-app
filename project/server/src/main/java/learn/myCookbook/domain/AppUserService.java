package learn.myCookbook.domain;

import learn.myCookbook.data.AppUserRepository;
import learn.myCookbook.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public List<AppUser> findAll() {
        return repository.findAll();
    }

    public AppUser findById(int userId) {
        return repository.findById(userId);
    }

    public AppUser findByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       AppUser user = repository.findByUserName(username);

       if (user == null || !user.isActive()) {
           throw new UsernameNotFoundException(username + "not found.");
       }

       List<GrantedAuthority> authorities = user.getRoles().stream()
               .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
               .collect(Collectors.toList());

        return new User(user.getUserName(), user.getPasswordHash(), authorities);
    }

    public boolean correctUserNamePassword(String userName, String passwordHash) {
        return repository.correctUserNamePassword(userName, passwordHash);
    }

    public Result<AppUser> add(AppUser user) {

        Result<AppUser> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("userId cannot be set for `add` operation.", ResultType.INVALID);
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

        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user = repository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validate(user);

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

    private Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AppUser>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<AppUser> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

       return result;
    }

    private Result<AppUser> checkDuplicateUserNameEmailOnUpdate(AppUser user) {
        Result<AppUser> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null.", ResultType.INVALID);
            return result;
        }

        if (!user.getEmail().equals(repository.findById(user.getUserId()).getEmail())) {
            if (repository.findByEmail(user.getEmail()) != null) {
                result.addMessage("That email is already taken.", ResultType.INVALID);
            }
        }

        if (!user.getUserName().equals(repository.findById(user.getUserId()).getUserName())) {
            if (repository.findByUserName(user.getUserName()) != null) {
                result.addMessage("That user name is already taken.", ResultType.INVALID);
            }
        }

        return result;
    }
}

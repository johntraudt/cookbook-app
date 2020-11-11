package learn.myCookbook.domain;

import learn.myCookbook.data.UserRepository;
import learn.myCookbook.models.User;
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

//    public Result<User> add(User user) {
//        Result<User>
//    }


}

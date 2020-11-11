package learn.myCookbook.data;

import learn.myCookbook.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(int userId) {
        return null;
    }

    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean deleteById(int userId) {
        return false;
    }
}

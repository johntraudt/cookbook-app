package learn.myCookbook.data;

import learn.myCookbook.models.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int userId);

    User findByUserName(String userName);

    User findByEmail(String email);

    boolean correctUserNamePassword(String userName, String passwordHash);

    User add(User user);

    boolean update(User user);

    boolean setUserNameEmail(int userId, String userName, String email);

    boolean deactivateById(int userId);
}

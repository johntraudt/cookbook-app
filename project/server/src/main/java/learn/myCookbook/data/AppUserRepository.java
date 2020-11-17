package learn.myCookbook.data;

import learn.myCookbook.models.AppUser;

import java.util.List;

public interface AppUserRepository {

    List<AppUser> findAll();

    AppUser findById(int userId);

    AppUser findByUserName(String userName);

    AppUser findByEmail(String email);

    boolean correctUserNamePassword(String userName, String passwordHash);

    AppUser add(AppUser user);

    boolean update(AppUser user);

    boolean setUserNameEmail(int userId, String userName, String email);

    boolean deactivateById(int userId);
}

package learn.myCookbook.data.mappers;

import learn.myCookbook.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser> {

    private boolean includeLogin;

    public AppUserMapper() {
    }

    public AppUserMapper(boolean includeLogin) {
        this.includeLogin = includeLogin;
    }

    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
        AppUser user = new AppUser();
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setActive(resultSet.getBoolean("is_active"));
        user.setUserRoleId(resultSet.getInt("user_role_id"));

//        if (includeLogin) {
//            user.setUserName(resultSet.getString("user_name"));
//            user.setPasswordHash(resultSet.getString("password_hash"));
//        }
        return user;
    }



}

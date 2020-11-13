package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper implements RowMapper<Login> {

    @Override
    public Login mapRow(ResultSet resultSet, int i) throws SQLException {
        Login login = new Login();
        login.setUserId(resultSet.getInt("user_id"));
        login.setUserName(resultSet.getString("user_name"));
        login.setPasswordHash(resultSet.getString("password_hash"));
        return login;
    }
}

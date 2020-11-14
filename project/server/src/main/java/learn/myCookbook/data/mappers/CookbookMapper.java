package learn.myCookbook.data.mappers;

import learn.myCookbook.models.Cookbook;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CookbookMapper implements RowMapper<Cookbook> {
    @Override
    public Cookbook mapRow(ResultSet resultSet, int i) throws SQLException {
        Cookbook cookbook = new Cookbook();
        cookbook.setCookbookId(resultSet.getInt("cookbook_id"));
        cookbook.setTitle(resultSet.getString("title"));
        cookbook.setPrivate(resultSet.getBoolean("is_private"));
        cookbook.setUserId(resultSet.getInt("user_id"));

        UserMapper userMapper = new UserMapper();
        cookbook.setUser(userMapper.mapRow(resultSet, i));

        return cookbook;
    }
}

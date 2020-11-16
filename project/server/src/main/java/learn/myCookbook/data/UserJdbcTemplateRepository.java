package learn.myCookbook.data;

import learn.myCookbook.data.mappers.LoginMapper;
import learn.myCookbook.data.mappers.UserMapper;
import learn.myCookbook.data.mappers.UserRoleMapper;
import learn.myCookbook.models.Login;
import learn.myCookbook.models.User;
import learn.myCookbook.models.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user limit 1000";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int userId) {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user "
                + "where user_id = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user);
            setRole(user);
        }

        return user;
    }

    @Override
    public User findByUserName(String userName) {
        final String sql = "select u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from user u " +
                "join login l on u.user_id = l.user_id " +
                "where l.user_name = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), userName).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user);
            setRole(user);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user "
                + "where email = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), email).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user);
            setRole(user);
        }

        return user;
    }

    @Override
    public boolean correctUserNamePassword(String userName, String passwordHash) {
        final String sql = "select l.user_id, l.user_name, l.password_hash " +
                "from login l " +
                "join user u on u.user_id = l.user_id " +
                "where l.user_name = ? " +
                "and l.password_hash = ?;";

        return jdbcTemplate.query(sql, new LoginMapper(), userName, passwordHash).size() > 0;
    }

    @Override
    public User add(User user) {
        final String sql = "insert into `user` (first_name, last_name, email, user_role_id) "
                + "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getUserRoleId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        user.setUserId(keyHolder.getKey().intValue());
        addLogin(user);
        //updateRoles(user);

        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "is_active = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActive(),
                user.getUserId()) > 0;
    }

    @Override
    public boolean setUserNameEmail(int userId, String userName, String email) {
        final String sql = "update user u, login l set " +
                "l.user_name = ?, " +
                "u.email = ? " +
                "where u.user_id = l.user_id " +
                "and u.user_id = ?;";

        return jdbcTemplate.update(sql, userName, email, userId) > 0;
    }

    @Override
    @Transactional
    public boolean deactivateById(int userId) {
        final String sql = "update user set " +
                "is_active = ? " +
                "where user_id = ? ";

        return jdbcTemplate.update(sql, 0, userId) > 0;
    }

    private void setRole(User user) {
        final String sql = "select ur.user_role_id, ur.user_role_name " +
                "from user_role ur " +
                "join user u on u.user_role_id = ur.user_role_id " +
                "where u.user_id = ?;";

        UserRole userRole = jdbcTemplate.query(sql, new UserRoleMapper(), user.getUserId())
                .stream()
                .findFirst().orElse(null);
        user.setRole(userRole);
    }

    private void setLogin(User user) {
        final String sql = "select l.user_id, l.user_name, l.password_hash " +
                "from login l " +
                "join user u on u.user_id = l.user_id " +
                "where u.user_id = ?;";

        Login login = jdbcTemplate.query(sql, new LoginMapper(), user.getUserId())
                .stream()
                .findFirst().orElse(null);
        user.setUserName(login.getUserName());
    }

    private boolean addLogin(User user) {
        final String sql = "insert into login (user_id, user_name, password_hash) " +
                "values (?,?,?);";

        return jdbcTemplate.update(sql, user.getUserId(), user.getUserName(), user.getPasswordHash()) > 0;
    }
}

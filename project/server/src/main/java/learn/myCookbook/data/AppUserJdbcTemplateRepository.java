package learn.myCookbook.data;

import learn.myCookbook.data.mappers.LoginMapper;
import learn.myCookbook.data.mappers.AppUserMapper;
import learn.myCookbook.data.mappers.UserRoleMapper;
import learn.myCookbook.models.AppUser;
import learn.myCookbook.models.Login;
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
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AppUser> findAll() {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user limit 1000";
        List<AppUser> all = jdbcTemplate.query(sql, new AppUserMapper());

        for (AppUser user : all) {
            if (user != null) {
                setLogin(user, false);
                setRole(user);
            }

            if (user != null && !user.isActive()) {
                user.setUserName("Deleted Account");
                user.setFirstName("Deleted");
                user.setLastName("Account");
            }
        }

        return all;
    }

    @Override
    public AppUser findById(int userId) {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user "
                + "where user_id = ?;";

        AppUser user = jdbcTemplate.query(sql, new AppUserMapper(), userId).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user, false);
            setRole(user);
        }

        if (user != null && !user.isActive()) {
            user.setUserName("Deleted Account");
            user.setFirstName("Deleted");
            user.setLastName("Account");
        }

        return user;
    }

    @Override
    public AppUser findByUserName(String userName) {
        final String sql = "select u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from user u " +
                "join login l on u.user_id = l.user_id " +
                "where l.user_name = ?;";

        AppUser user = jdbcTemplate.query(sql, new AppUserMapper(true), userName).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user, true);
            setRole(user);
        }

        return user;
    }

    @Override
    public AppUser findByEmail(String email) {
        final String sql = "select user_id, first_name, last_name, email, is_active, user_role_id "
                + "from user "
                + "where email = ?;";

        AppUser user = jdbcTemplate.query(sql, new AppUserMapper(), email).stream()
                .findFirst().orElse(null);

        if (user != null) {
            setLogin(user, false);
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
    @Transactional
    public AppUser add(AppUser user) {
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
        addRoles(user);

        return user;
    }

    @Override
    @Transactional
    public boolean update(AppUser user) {
        final String sql = "update user set first_name = ?, last_name = ?, email = ?, is_active = ? " +
                "where user_id = ?;";

        jdbcTemplate.update("update login set user_name = ? where user_id = ?;", user.getUserName(), user.getUserId());
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

    private void setRole(AppUser user) {
        final String sql = "select ur.user_role_id, ur.user_role_name " +
                "from user_role ur " +
                "join user u on u.user_role_id = ur.user_role_id " +
                "where u.user_id = ?;";

        UserRole userRole = jdbcTemplate.query(sql, new UserRoleMapper(), user.getUserId())
                .stream()
                .findFirst().orElse(null);
        user.setRole(userRole);
    }

    private void setLogin(AppUser user, boolean includePassword) {
        final String sql = "select l.user_id, l.user_name, l.password_hash " +
                "from login l " +
                "join user u on u.user_id = l.user_id " +
                "where u.user_id = ?;";

        Login login = jdbcTemplate.query(sql, new LoginMapper(), user.getUserId())
                .stream()
                .findFirst().orElse(null);
        user.setUserName(login.getUserName());
        if (includePassword) {
            user.setPasswordHash(login.getPasswordHash());
        }
    }

    private boolean addLogin(AppUser user) {
        final String sql = "insert into login (user_id, user_name, password_hash) " +
                "values (?,?,?);";

        return jdbcTemplate.update(sql, user.getUserId(), user.getUserName(), user.getPasswordHash()) > 0;
    }

    private void addRoles(AppUser user) {

    }
}

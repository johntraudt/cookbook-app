package learn.myCookbook.data;

import learn.myCookbook.data.mappers.CookbookMapper;
import learn.myCookbook.models.Cookbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CookbookJdbcTemplateRepository implements CookbookRepository{

    private final JdbcTemplate jdbcTemplate;

    public CookbookJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cookbook> findAll() {
        final String sql = "select cookbook_id, title, is_private, user_id " +
                "from cookbook limit 1000;";

        return jdbcTemplate.query(sql, new CookbookMapper());
    }

    @Override
    public Cookbook findById(int cookbookId) {
        final String sql = "select cookbook_id, title, is_private, user_id " +
                "from cookbook where cookbook_id = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), cookbookId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cookbook> findAllByUserId(int userId) {
        final String sql = "select cookbook_id, title, is_private, user_id " +
                "from cookbook where user_id = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), userId);
    }

    @Override
    public List<Cookbook> findPublicByUserId(int userId) {
        final String sql = "select cookbook_id, title, is_private, user_id " +
                "from cookbook where user_id = ? " +
                "and is_private = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), userId, 0);
    }

    @Override
    public List<Cookbook> findAllByRecipeId(int recipeId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id " +
                "from cookbook c " +
                "join cookbook_recipe cr on c.cookbook_id = cr.cookbook_id " +
                "where cr.recipe_id = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), recipeId);
    }

    @Override
    public List<Cookbook> findPublicByRecipeId(int recipeId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id " +
                "from cookbook c " +
                "join cookbook_recipe cr on c.cookbook_id = cr.cookbook_id " +
                "where cr.recipe_id = ? " +
                "and c.is_private = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), recipeId, 0);
    }

    @Override
    public List<Cookbook> findPublicByTitle(String title) {
        final String sql = "select cookbook_id, title, is_private, user_id " +
                "from cookbook where title = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), title);
    }

    @Override
    public Cookbook add(Cookbook cookbook) {
        final String sql = "insert into cookbook " +
                "(cookbook_id, title, is_private, user_id) " +
                "values " +
                "(?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cookbook.getCookbookId());
            ps.setString(2, cookbook.getTitle());
            ps.setBoolean(3, cookbook.isPrivate());
            ps.setInt(4, cookbook.getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        cookbook.setCookbookId(keyHolder.getKey().intValue());
        return cookbook;
    }

    @Override
    public boolean update(Cookbook cookbook) {
        final String sql = "update cookbook set " +
                "title = ?, " +
                "is_private = ?, " +
                "user_id = ? " +
                "where cookbook_id = ?;";

        return jdbcTemplate.update(sql,
                cookbook.getTitle(),
                cookbook.isPrivate(),
                cookbook.getUserId(),
                cookbook.getCookbookId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int cookbookId) {
        jdbcTemplate.update("delete from cookbook_recipe where cookbook_id = ?;", cookbookId);
        return jdbcTemplate.update("delete from cookbook where cookbook_id = ?;", cookbookId) > 0;
    }
}

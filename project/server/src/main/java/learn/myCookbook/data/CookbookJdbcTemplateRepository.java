package learn.myCookbook.data;

import learn.myCookbook.data.mappers.CookbookMapper;
import learn.myCookbook.data.mappers.RecipeMapper;
import learn.myCookbook.data.mappers.RecipeTagCategoryMapper;
import learn.myCookbook.models.Cookbook;
import learn.myCookbook.models.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CookbookJdbcTemplateRepository implements CookbookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AppUserRepository appUserRepository;
    private final RecipeRepository recipeRepository;

    public CookbookJdbcTemplateRepository(JdbcTemplate jdbcTemplate, AppUserRepository appUserRepository, RecipeRepository recipeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.appUserRepository = appUserRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Cookbook> findAll() {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id limit 1000;";

        return jdbcTemplate.query(sql, new CookbookMapper());
    }

    @Override
    public Cookbook findById(int cookbookId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where cookbook_id = ?;";

        Cookbook cookbook = jdbcTemplate.query(sql, new CookbookMapper(), cookbookId)
                .stream()
                .findFirst()
                .orElse(null);

        if (cookbook != null) {
            cookbook.setUser(appUserRepository.findById(cookbook.getUserId()));
            cookbook.setRecipes(recipeRepository.findByCookbookId(cookbookId));
        }


        return cookbook;
    }

    @Override
    public List<Cookbook> findAllByUserId(int userId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where c.user_id = ?;";

        List<Cookbook> cookbooks = jdbcTemplate.query(sql, new CookbookMapper(), userId);
        for (Cookbook cookbook : cookbooks) {
            if (cookbook != null) {
                cookbook.setUser(appUserRepository.findById(cookbook.getUserId()));
                cookbook.setRecipes(recipeRepository.findByCookbookId(cookbook.getCookbookId()));
            }
        }

        return cookbooks;
    }

    @Override
    public List<Cookbook> findPublicByUserId(int userId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where c.user_id = ? " +
                "and c.is_private = ?;";

        List<Cookbook> cookbooks = jdbcTemplate.query(sql, new CookbookMapper(), userId, 0);
        for (Cookbook cookbook : cookbooks) {
            if (cookbook != null) {
                cookbook.setUser(appUserRepository.findById(cookbook.getUserId()));
                cookbook.setRecipes(recipeRepository.findByCookbookId(cookbook.getCookbookId()));
            }
        }

        return cookbooks;
    }

    @Override
    public Cookbook findByUserIdAndTitle(int userId, String title) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where c.user_id = ? " +
                "and c.title = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), userId, title)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cookbook> findAllByRecipeId(int recipeId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, " +
                "u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "join cookbook_recipe cr on c.cookbook_id = cr.cookbook_id " +
                "where cr.recipe_id = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), recipeId);
    }

    @Override
    public List<Cookbook> findPublicByRecipeId(int recipeId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, " +
                "u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "join cookbook_recipe cr on c.cookbook_id = cr.cookbook_id " +
                "where cr.recipe_id = ? " +
                "and c.is_private = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), recipeId, 0);
    }

    @Override
    public List<Cookbook> findPublicByTitle(String title) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where c.title like ? " +
                "and c.is_private = ?;";
        return jdbcTemplate.query(sql, new CookbookMapper(), "%" + title + "%", 0);
    }

    @Override
    public List<Recipe> findRecipesByCookBookId(int cookbookId) {
        final String sql = "select r.recipe_id, r.user_id, r.recipe_name, r.prep_time, r.cook_time, r.servings, r.date, r.was_updated, r.is_featured, r.calories, r.image_link, r.user_id " +
                "from recipe r " +
                "join cookbook_recipe cr on r.recipe_id = cr.recipe_id " +
                "where cr.cookbook_id = ?;";

        return jdbcTemplate.query(sql, new RecipeMapper(), cookbookId);
    }

    @Override
    public boolean titleTakenForUser(int userId, String title) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "where c.user_id = ? " +
                "and c.title = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), userId, title).size() > 0;
    }

    @Override
    public boolean recipeIsInCookbook(int cookbookId, int recipeId) {
        final String sql = "select c.cookbook_id, c.title, c.is_private, c.user_id, u.user_id, u.first_name, u.last_name, u.email, u.is_active, u.user_role_id " +
                "from cookbook c " +
                "join user u on c.user_id = u.user_id " +
                "join cookbook_recipe cr on c.cookbook_id = cr.cookbook_id " +
                "where cr.cookbook_id = ? and cr.recipe_id = ?;";

        return jdbcTemplate.query(sql, new CookbookMapper(), cookbookId, recipeId).size() > 0;
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
    public boolean insertRecipeById(int cookBookId, int recipeId) {
        final String sql = "insert into cookbook_recipe " +
                "(cookbook_id, recipe_id) " +
                "values " +
                "(?, ?)";

        return jdbcTemplate.update(sql, cookBookId, recipeId) > 0;
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
    public boolean setTitle(int cookbookId, String title) {
        final String sql = "update cookbook set " +
                "title = ? " +
                "where cookbook_id = ?;";

        return jdbcTemplate.update(sql, title, cookbookId) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int cookbookId) {
        jdbcTemplate.update("delete from cookbook_recipe where cookbook_id = ?;", cookbookId);
        return jdbcTemplate.update("delete from cookbook where cookbook_id = ?;", cookbookId) > 0;
    }

    @Override
    public boolean removeRecipeById(int cookBookId, int recipeId) {
        return jdbcTemplate.update("delete from cookbook_recipe where cookbook_id = ? and recipe_id = ?;", cookBookId, recipeId) > 0;
    }
}

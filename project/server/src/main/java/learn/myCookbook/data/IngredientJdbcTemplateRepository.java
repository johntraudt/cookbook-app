package learn.myCookbook.data;

import learn.myCookbook.data.mappers.IngredientMapper;
import learn.myCookbook.models.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class IngredientJdbcTemplateRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        final String sql = "select ingredient_id, name " +
                "from ingredient limit 1000;";
        return jdbcTemplate.query(sql, new IngredientMapper());
    }

    @Override
    public Ingredient findById(int ingredientId) {
        final String sql = "select ingredient_id, name " +
                "from ingredient " +
                "where ingredient_id = ?;";

        return jdbcTemplate.query(sql, new IngredientMapper(), ingredientId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Ingredient findByName(String name) {
        final String sql = "select ingredient_id, name " +
                "from ingredient " +
                "where `name` = ?;";

        return jdbcTemplate.query(sql, new IngredientMapper(), name)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        final String sql = "insert into ingredient (ingredient_id, `name`) " +
                "values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ingredient.getIngredientId());
            ps.setString(1, ingredient.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        ingredient.setIngredientId(keyHolder.getKey().intValue());
        return  ingredient;
    }

    @Override
    public boolean update(Ingredient ingredient) {
        final String sql = "update ingredient set " +
                "`name` = ? " +
                "where ingredient_id = ?;";

        return jdbcTemplate.update(sql,
                ingredient.getName()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int ingredientId) {
        jdbcTemplate.update("delete from recipe_ingredient where ingredient_id = ?;", ingredientId);
        return jdbcTemplate.update("delete from ingredient where ingredient_id = ?;", ingredientId) > 0;
    }
}

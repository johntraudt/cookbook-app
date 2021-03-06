package learn.myCookbook.data;

import learn.myCookbook.data.mappers.RecipeIngredientMapper;
import learn.myCookbook.models.RecipeIngredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RecipeIngredientJdbcTemplateRepository implements RecipeIngredientRepository{
    private final JdbcTemplate jdbcTemplate;

    public RecipeIngredientJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeIngredient> findAll() {
        final String sql = "select " +
                "ri.recipe_ingredient_id, ri.recipe_id, ri.ingredient_id, ri.ingredient_list_index, ri.quantity, ri.measurement_unit_id, i.ingredient_name, mu.measurement_unit_name " +
                "from recipe_ingredient ri " +
                "join ingredient i on ri.ingredient_id = i.ingredient_id " +
                "left join measurement_unit mu on ri.measurement_unit_id = mu.measurement_unit_id " +
                "limit 1000;";

        return jdbcTemplate.query(sql, new RecipeIngredientMapper());
    }

    @Override
    public List<RecipeIngredient> findByRecipeId(int recipeId) {
        final String sql = "select ri.recipe_ingredient_id, ri.recipe_id, ri.ingredient_id, ri.ingredient_list_index, ri.quantity, ri.measurement_unit_id, i.ingredient_name, mu.measurement_unit_name " +
                "from recipe_ingredient ri " +
                "join ingredient i on ri.ingredient_id = i.ingredient_id " +
                "left join measurement_unit mu on ri.measurement_unit_id = mu.measurement_unit_id " +
                "where recipe_id = ?;";

        return jdbcTemplate.query(sql, new RecipeIngredientMapper(), recipeId);
    }

    @Override
    public RecipeIngredient findById(int recipeIngredientId) {
        final String sql = "select " +
                "ri.recipe_ingredient_id, ri.recipe_id, ri.ingredient_id, ri.ingredient_list_index, ri.quantity, ri.measurement_unit_id, i.ingredient_name, mu.measurement_unit_name " +
                "from recipe_ingredient ri " +
                "join ingredient i on ri.ingredient_id = i.ingredient_id " +
                "left join measurement_unit mu on ri.measurement_unit_id = mu.measurement_unit_id " +
                "where recipe_ingredient_id = ?;";

        return jdbcTemplate.query(sql, new RecipeIngredientMapper(), recipeIngredientId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public RecipeIngredient findByRecipeIdAndIndex(int recipeId, int ingredientListIndex) {
        final String sql = "select " +
                "ri.recipe_ingredient_id, ri.recipe_id, ri.ingredient_id, ri.ingredient_list_index, ri.quantity, ri.measurement_unit_id, i.ingredient_name, mu.measurement_unit_name " +
                "from recipe_ingredient ri " +
                "join ingredient i on ri.ingredient_id = i.ingredient_id " +
                "left join measurement_unit mu on ri.measurement_unit_id = mu.measurement_unit_id " +
                "where ri.recipe_id = ? and ri.ingredient_list_index = ?;";

        return jdbcTemplate.query(sql, new RecipeIngredientMapper(), recipeId, ingredientListIndex)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public RecipeIngredient add(RecipeIngredient recipeIngredient) {
        final String sql = "insert into recipe_ingredient " +
                "(recipe_ingredient_id, recipe_id, ingredient_id, ingredient_list_index, quantity, measurement_unit_id) " +
                "values " +
                "(?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, recipeIngredient.getRecipeIngredientId());
            ps.setInt(2, recipeIngredient.getRecipeId());
            ps.setInt(3, recipeIngredient.getIngredientId());
            ps.setInt(4, recipeIngredient.getIngredientListIndex());
            ps.setString(5, recipeIngredient.getQuantity());
            ps.setInt(6, recipeIngredient.getMeasurementUnitId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        recipeIngredient.setRecipeIngredientId(keyHolder.getKey().intValue());
        return recipeIngredient;
    }

    @Override
    public boolean update(RecipeIngredient recipeIngredient) {
        final String sql = "update recipe_ingredient set " +
                "recipe_id = ?, " +
                "ingredient_id = ?, " +
                "ingredient_list_index = ?, " +
                "measurement_unit_id = ? " +
                "where recipe_ingredient_id = ?;";

        return jdbcTemplate.update(sql,
                recipeIngredient.getRecipeId(),
                recipeIngredient.getIngredientId(),
                recipeIngredient.getIngredientListIndex(),
                recipeIngredient.getMeasurementUnitId(),
                recipeIngredient.getRecipeIngredientId()) > 0;
    }

    @Override
    public boolean deleteById(int recipeIngredientId) {
        final String sql = "delete from recipe_ingredient " +
                "where recipe_ingredient_id = ?;";

        return jdbcTemplate.update(sql,  recipeIngredientId) > 0;
    }

    @Override
    public boolean deleteByRecipeId(int recipeId) {
        final String sql = "delete from recipe_ingredient " +
                "where recipe_id = ?;";

        return jdbcTemplate.update(sql, new RecipeIngredientMapper(), recipeId) > 0;
    }

    @Override
    public boolean deleteByRecipeIdAndIndex(int recipeId, int ingredientListIndex) {
        final String sql = "delete from recipe_ingredient " +
                "where recipe_id = ? " +
                "and ingredient_list_index = ?;";

        return jdbcTemplate.update(sql, new RecipeIngredientMapper(), recipeId, ingredientListIndex) > 0;
    }
}
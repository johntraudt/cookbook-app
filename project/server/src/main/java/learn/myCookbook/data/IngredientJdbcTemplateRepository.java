package learn.myCookbook.data;

import learn.myCookbook.data.mappers.IngredientMapper;
import learn.myCookbook.models.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientJdbcTemplateRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        final String sql = "select ingredient_id, name" +
                "from ingredient limit 1000";
        return jdbcTemplate.query(sql, new IngredientMapper());
    }

    @Override
    public Ingredient findById() {
        return null;
    }

    @Override
    public Ingredient findByName() {
        return null;
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        return null;
    }

    @Override
    public boolean update(Ingredient ingredient) {
        return false;
    }

    @Override
    public boolean deleteById(Ingredient ingredient) {
        return false;
    }
}

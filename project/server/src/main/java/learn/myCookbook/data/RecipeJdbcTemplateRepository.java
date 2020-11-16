package learn.myCookbook.data;

import learn.myCookbook.data.mappers.*;
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
public class RecipeJdbcTemplateRepository implements RecipeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AppUserJdbcTemplateRepository userRepository;
    private final ReviewRepository reviewRepository;

    public RecipeJdbcTemplateRepository(JdbcTemplate jdbcTemplate, AppUserJdbcTemplateRepository userRepository, ReviewRepository reviewRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Recipe> findAll() {

        final String sql = "select recipe_id, recipe_name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe limit 1000;";

        List<Recipe> all = jdbcTemplate.query(sql, new RecipeMapper());

        for (Recipe recipe : all) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            addReviews(recipe);
        }

        return all;
    }

    @Override
    public Recipe findById(int recipeId) {

        final String sql = "select recipe_id, recipe_name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe " +
                "where recipe_id = ?;";

        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(), recipeId)
                .stream()
                .findFirst().orElse(null);

        if (recipe != null) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            recipe.setReviews(reviewRepository.findByRecipeId(recipe.getRecipeId()));
            addDirections(recipe);
            addTags(recipe);
            addIngredients(recipe);
        }

        return recipe;
    }

    @Override
    public List<Recipe> findByName(String recipeName) {
        final String sql = "select recipe_id, recipe_name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe " +
                "where recipe_name like ?;";

        List<Recipe> recipes = jdbcTemplate.query(sql, new RecipeMapper(),  "%" + recipeName + "%");
        for (Recipe recipe : recipes) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            addReviews(recipe);
        }

        return recipes;
    }

    @Override
    public Recipe findByUserIdAndName(int userId, String name) {
        final String sql = "select recipe_id, recipe_name, prep_time, cook_time, servings, date, was_updated, is_featured, calories, image_link, user_id " +
                "from recipe " +
                "where user_id = ? " +
                "and recipe_name = ?;";

        Recipe recipe = jdbcTemplate.query(sql, new RecipeMapper(), userId, name)
                .stream()
                .findFirst().orElse(null);

        if (recipe != null) {
            recipe.setUser(userRepository.findById(recipe.getUserId()));
            addReviews(recipe);
            addDirections(recipe);
            addTags(recipe);
            addIngredients(recipe);
        }

        return recipe;
    }

    @Override
    public int findRandomRecipeId() {
        List<Recipe> all = findAll();
        int index = (int) (Math.random() * all.size());
        return all.get(index).getRecipeId();
    }

    @Override
    public Recipe add(Recipe recipe) {
        final String sql = "insert into recipe (recipe_name, prep_time, cook_time, servings, `date`, was_updated, is_featured, calories, user_id, image_link) " +
                "values (?,?,?,?,?,?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recipe.getName());
            ps.setInt(2, recipe.getPrepTimeInMinutes());
            ps.setInt(3, recipe.getCookTimeInMinutes());
            ps.setInt(4, recipe.getServings());
            ps.setObject(5, recipe.getDate());
            ps.setBoolean(6, recipe.isWasUpdated());
            ps.setBoolean(7, recipe.isFeatured());
            ps.setInt(8, recipe.getCalories());
            ps.setInt(9, recipe.getUserId());
            ps.setString(10, recipe.getImageLink());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        recipe.setRecipeId(keyHolder.getKey().intValue());
        return recipe;
    }

    @Override
    public boolean update(Recipe recipe) {
        final String sql = "update recipe set " +
                "recipe_name = ?, " +
                "prep_time = ?, " +
                "cook_time = ?, " +
                "servings = ?, " +
                "`date` = ?, " +
                "was_updated = ?, " +
                "is_featured = ?, " +
                "calories = ?, " +
                "user_id = ?, " +
                "image_link = ? " +
                "where recipe_id = ?;";

        return jdbcTemplate.update(sql,
                recipe.getName(),
                recipe.getPrepTimeInMinutes(),
                recipe.getCookTimeInMinutes(),
                recipe.getServings(),
                recipe.getDate(),
                recipe.isWasUpdated(),
                recipe.isFeatured(),
                recipe.getCalories(),
                recipe.getUserId(),
                recipe.getImageLink(),
                recipe.getRecipeId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int recipeId) {
        jdbcTemplate.update("delete from recipe_ingredient where recipe_id = ?;", recipeId);
        jdbcTemplate.update("delete from direction where recipe_id = ?;", recipeId);
        jdbcTemplate.update("delete from cookbook_recipe where recipe_id = ?;", recipeId);
        jdbcTemplate.update("delete from recipe_recipe_tag where recipe_id = ?;", recipeId);
        jdbcTemplate.update("delete from review where recipe_id = ?;", recipeId);
        return jdbcTemplate.update("delete from recipe where recipe_id = ?;", recipeId) > 0;
    }

    private void addIngredients(Recipe recipe) {
        final String sql = "select ri.recipe_ingredient_id, r.recipe_id, ri.quantity, ri.ingredient_id, ri.ingredient_list_index, mu.measurement_unit_id, i.ingredient_name, mu.measurement_unit_name " +
                "from recipe_ingredient ri " +
                "join recipe r on r.recipe_id = ri.recipe_id " +
                "join ingredient i on i.ingredient_id = ri.ingredient_id " +
                "left join measurement_unit mu on mu.measurement_unit_id = ri.measurement_unit_id " +
                "where r.recipe_id = ?;";

        var ingredients = jdbcTemplate.query(sql, new RecipeIngredientMapper(), recipe.getRecipeId());
        recipe.setIngredients(ingredients);
    }

    private void addTags(Recipe recipe) {
        final String sql = "select rt.recipe_tag_id, rt.recipe_tag_name, rt.tag_image_link, rt.recipe_tag_category_id " +
                "from recipe_tag rt " +
                "join recipe_recipe_tag rrt on rrt.recipe_tag_id = rt.recipe_tag_id " +
                "join recipe r on r.recipe_id = rrt.recipe_id " +
                "where r.recipe_id = ?;";

        var tags = jdbcTemplate.query(sql, new RecipeTagMapper(), recipe.getRecipeId());
        recipe.setTags(tags);
    }

    private void addDirections(Recipe recipe) {
        final String sql = "select d.direction_id, d.recipe_id, d.direction_number, d.direction_text " +
                "from direction d " +
                "join recipe r on r.recipe_id = d.recipe_id " +
                "where r.recipe_id = ?;";

        var directions = jdbcTemplate.query(sql, new DirectionMapper(), recipe.getRecipeId());
        recipe.setDirections(directions);
    }

    private void addReviews(Recipe recipe) {
        final String sql = "select re.review_id, re.rating, re.comment, re.review_date, re.user_id, re.recipe_id " +
                "from review re " +
                "join recipe r on r.recipe_id = re.recipe_id " +
                "where r.recipe_id = ?;";

        var reviews = jdbcTemplate.query(sql, new ReviewMapper(), recipe.getRecipeId());
        recipe.setReviews(reviews);
    }
}

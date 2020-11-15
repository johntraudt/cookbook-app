package learn.myCookbook.data;

import learn.myCookbook.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeJdbcTemplateRepositoryTest {

    @Autowired
    RecipeJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Recipe> recipes = repository.findAll();

        assertNotNull(recipes);
        assertTrue(recipes.size() == 4);
    }

    @Test
    void shouldFindById() {
        Recipe result = repository.findById(3);

        assertEquals(3, result.getRecipeId());
        assertEquals("garden salad", result.getName());
    }

    @Test
    void shouldAdd() {
        Recipe recipe = makeRecipe();
        Recipe actual = repository.add(recipe);

        assertNotNull(actual);
        assertTrue(recipe.getRecipeId() >= 5);
    }

    @Test
    void shouldUpdate() {
        Recipe recipe = makeRecipe();
        recipe.setName("Now with more chili");
        recipe.setRecipeId(3);

        assertTrue(repository.update(recipe));
    }

    @Test
    void shouldNotUpdateMissing() {
        Recipe recipe = makeRecipe();
        recipe.setRecipeId(200);

        assertFalse(repository.update(recipe));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private Recipe makeRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Test with chili");
        recipe.setPrepTimeInMinutes(15);
        recipe.setCookTimeInMinutes(25);
        recipe.setServings(4);
        recipe.setDate(LocalDate.of(2020, 11, 4));
        recipe.setWasUpdated(true);
        recipe.setFeatured(false);
        recipe.setCalories(350);
        recipe.setImageLink("img.png");
        recipe.setUserId(1);
        return recipe;
    }

}
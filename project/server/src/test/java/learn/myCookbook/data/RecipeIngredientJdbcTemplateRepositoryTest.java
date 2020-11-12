package learn.myCookbook.data;

import learn.myCookbook.models.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeIngredientJdbcTemplateRepositoryTest {

    @Autowired
    RecipeIngredientJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<RecipeIngredient> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 10);
    }

    @Test
    void shouldFindById() {
        RecipeIngredient recipeIngredient = repository.findById(2);
        assertNotNull(recipeIngredient);
        assertEquals(2, recipeIngredient.getIngredientId());
    }

    @Test
    void shouldNotFindByMissingId() {
        RecipeIngredient missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindByRecipeId() {
        List<RecipeIngredient> list = repository.findByRecipeId(2);
        assertNotNull(list);
        assertTrue(list.size() >= 5);
    }

    @Test
    void shouldNotFindByMissingRecipeId () {
        List<RecipeIngredient> empty = repository.findByRecipeId(999);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldFindByRecipeIdAndIndex() {
        RecipeIngredient recipeIngredient = repository.findByRecipeIdAndIndex(2, 1);
        assertNotNull(recipeIngredient);
        assertEquals(2, recipeIngredient.getIngredientId());
    }

    @Test
    void shouldNotFindByMissingRecipeIdOrIndex() {
        RecipeIngredient missing = repository.findByRecipeIdAndIndex(999, 1);
        assertNull(missing);
        missing = repository.findByRecipeIdAndIndex(2, 999);
        assertNull(missing);
    }

    @Test
    void shouldUpdate() {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeIngredientId(3);
        recipeIngredient.setIngredientListIndex(2);
        recipeIngredient.setNumerator(3);
        recipeIngredient.setRecipeId(2);
        recipeIngredient.setIngredientId(3);
        recipeIngredient.setMeasurementUnitId(1);

        assertTrue(repository.update(recipeIngredient));
        assertTrue(repository.findById(3).getNumerator() > 2);
    }

    @Test
    void shouldNotUpdateMissing() {

    }
}


package learn.myCookbook.data;

import learn.myCookbook.models.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Disabled
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
        for (RecipeIngredient ri : all) {
            System.out.println(ri.getRecipeIngredientId());
        }
        assertTrue(all.size() >= 5);
    }

    @Test
    void shouldFindById() {
        RecipeIngredient recipeIngredient = repository.findById(9);
        assertNotNull(recipeIngredient);
        assertEquals("8", recipeIngredient.getQuantity());
    }

    @Test
    void shouldNotFindByMissingId() {
        RecipeIngredient missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindByRecipeId() {
        List<RecipeIngredient> list = repository.findByRecipeId(3);
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
        RecipeIngredient recipeIngredient = repository.findByRecipeIdAndIndex(3, 2);
        assertNotNull(recipeIngredient);
    }

    @Test
    void shouldNotFindByMissingRecipeIdOrIndex() {
        RecipeIngredient missing = repository.findByRecipeIdAndIndex(999, 1);
        assertNull(missing);
        missing = repository.findByRecipeIdAndIndex(2, 999);
        assertNull(missing);
    }

    @Test
    void shouldAdd() {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeIngredientId(0);
        recipeIngredient.setIngredientListIndex(10);
        recipeIngredient.setRecipeId(3);
        recipeIngredient.setIngredientId(3);
        recipeIngredient.setMeasurementUnitId(1);

        recipeIngredient = repository.add(recipeIngredient);
        assertNotNull(recipeIngredient);
        assertTrue(recipeIngredient.getRecipeIngredientId() > 10);
    }

    @Test
    void shouldUpdate() {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeIngredientId(10);
        recipeIngredient.setIngredientListIndex(2);
        recipeIngredient.setRecipeId(3);
        recipeIngredient.setIngredientId(3);
        recipeIngredient.setMeasurementUnitId(1);

        assertTrue(repository.update(recipeIngredient));
    }

    @Test
    void shouldNotUpdateMissing() {
        RecipeIngredient missing = new RecipeIngredient();
        missing.setRecipeIngredientId(999);
        missing.setIngredientListIndex(2);
        missing.setRecipeId(2);
        missing.setIngredientId(3);
        missing.setMeasurementUnitId(1);

        assertFalse(repository.update(missing));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(8));
        assertFalse(repository.deleteById(8));
    }



}


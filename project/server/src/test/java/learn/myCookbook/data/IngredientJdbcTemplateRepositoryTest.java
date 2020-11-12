package learn.myCookbook.data;

import learn.myCookbook.models.Ingredient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IngredientJdbcTemplateRepositoryTest {

    @Autowired
    IngredientJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Ingredient> all = repository.findAll();
        assertTrue(all.size() >= 7);
    }

    @Test
    void shouldFindById() {
        Ingredient chicken = repository.findById(1);
        assertNotNull(chicken);
        assertEquals("chicken", chicken.getName());
    }

    @Test
    void shouldNotFindByMissingID() {
        Ingredient missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindByName() {
        Ingredient chicken = repository.findByName("chicken");
        assertNotNull(chicken);
        assertEquals(1, chicken.getIngredientId());
    }

    @Test
    void shouldNotFindByMissingName() {
        Ingredient missing = repository.findByName("missing name");
        assertNull(missing);
    }

    @Test
    void shouldAdd() {
        Ingredient ingredient = new Ingredient(0, "new ingredient");
        Ingredient result = repository.add(ingredient);
        assertNotNull(result);
        assertTrue(result.getIngredientId() > 11);
    }

    @Test
    void shouldUpdate() {
        Ingredient ingredient = new Ingredient(2, "spud");
        assertTrue(repository.update(ingredient));
        assertEquals("spud", repository.findById(2).getName());
        assertNull(repository.findByName("russet potato"));
    }

    @Test
    void shouldNotUpdateMissing() {
        Ingredient missing = new Ingredient(999, "missing ingredient");
        assertFalse(repository.update(missing));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(12));
        assertFalse(repository.deleteById(12));
    }
}

package learn.myCookbook.domain;

import learn.myCookbook.data.IngredientRepository;
import learn.myCookbook.models.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class IngredientServiceTest {

    @Autowired
    IngredientService service;

    @MockBean
    IngredientRepository repository;

    @Test
    void shouldNotAddNull() {
        Ingredient ingredient = null;
        Result<Ingredient> result = service.add(ingredient);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithId() {
        Ingredient ingredient = makeIngredient();
        ingredient.setIngredientId(1);
        Result<Ingredient> result = service.add(ingredient);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidName() {
        Ingredient ingredient = makeIngredient();
        ingredient.setName(null);
        Result<Ingredient> result = service.add(ingredient);
        assertEquals(ResultType.INVALID, result.getType());

        ingredient.setName(" ");
        result = service.add(ingredient);
        assertEquals(ResultType.INVALID, result.getType());
    }

    Ingredient makeIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Nirnroot");
        return ingredient;
    }

}
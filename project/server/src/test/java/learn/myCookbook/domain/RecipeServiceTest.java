package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RecipeServiceTest {

    @Autowired
    RecipeService service;

    @MockBean
    RecipeRepository repository;

    @Test
    void shouldNotAddNull() {
        Recipe recipe = null;
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithId() {
        Recipe recipe = makeRecipe();
        recipe.setRecipeId(2);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidName() {
        Recipe recipe = makeRecipe();
        recipe.setName(null);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe.setName(" ");
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidTime() {
        Recipe recipe = makeRecipe();
        recipe.setPrepTimeInMinutes(-1);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe.setCookTimeInMinutes(-1);
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidServings() {
        Recipe recipe = makeRecipe();
        recipe.setServings(0);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidDate() {
        Recipe recipe = makeRecipe();
        recipe.setDate(null);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidImageLink() {
        Recipe recipe = makeRecipe();
        recipe.setImageLink(null);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());

        recipe.setImageLink(" ");
        result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldSetIdForUpdate() {
        Recipe recipe = makeRecipe();
        recipe.setRecipeId(1);

        when(repository.update(recipe)).thenReturn(true);
        Result<Recipe> actual = service.update(recipe);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        Recipe recipe = makeRecipe();
        recipe.setRecipeId(1000);

        when(repository.update(recipe)).thenReturn(false);
        Result<Recipe> actual = service.update(recipe);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    Recipe makeRecipe() {
        Recipe recipe = new Recipe();
        recipe.setUserId(1);
        recipe.setName("Jumbo Test");
        recipe.setPrepTimeInMinutes(5);
        recipe.setCookTimeInMinutes(15);
        recipe.setServings(2);
        recipe.setDate(LocalDate.of(2020, 11, 9));
        recipe.setWasUpdated(false);
        recipe.setFeatured(true);
        recipe.setImageLink("img.org");
        return recipe;
    }

}
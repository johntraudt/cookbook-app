package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.Recipe;
import learn.myCookbook.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
        recipe.setRecipeId(1);
        Result<Recipe> result = service.add(recipe);
        assertEquals(ResultType.INVALID, result.getType());
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
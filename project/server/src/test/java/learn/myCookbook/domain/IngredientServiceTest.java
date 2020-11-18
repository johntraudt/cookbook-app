package learn.myCookbook.domain;

import learn.myCookbook.data.IngredientRepository;
import learn.myCookbook.models.Ingredient;
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





    Ingredient makeIngredient() {
        Ingredient ingredient = new Ingredient();

        return ingredient;
    }

}
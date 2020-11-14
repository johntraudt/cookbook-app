package learn.myCookbook.data;

import learn.myCookbook.models.RecipeTagCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeTagCategoryJdbcTemplateRepositoryTest {

    @Autowired
    RecipeTagCategoryJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<RecipeTagCategory> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);
    }
}

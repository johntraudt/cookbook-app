package learn.myCookbook.data;

import learn.myCookbook.models.RecipeTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeTagJdbcTemplateRepositoryTest {
    @Autowired
    RecipeTagJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<RecipeTag> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 7);
    }
}

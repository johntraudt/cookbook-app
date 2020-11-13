package learn.myCookbook.data;

import learn.myCookbook.models.Cookbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CookbookJdbcTemplateRepositoryTest {

    @Autowired
    CookbookJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Cookbook> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 2);
    }

    @Test
    void shouldFindById() {
        Cookbook cookbook = repository.findById(1);
        assertNotNull(cookbook);
        assertEquals("Assorted Recipes", cookbook.getTitle());
    }

    @Test
    void shouldNotFindByMissingId() {
        Cookbook missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindAllByUserId() {
        List<Cookbook> list = repository.findAllByUserId(1);
        assertNotNull(list);
        assertEquals("Assorted Recipes", list.get(0).getTitle());
    }

    @Test
    void shouldFindPublicByUserId() {
        List<Cookbook> notEmpty = repository.findPublicByUserId(1);
        assertNotNull(notEmpty);
        assertEquals(1, notEmpty.size());

        List<Cookbook> empty = repository.findPublicByUserId(2);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldNotFindByMissingUserId() {
        List<Cookbook> empty = repository.findAllByUserId(999);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldFindAllByRecipeId() {
        List<Cookbook> list = repository.findAllByRecipeId(2);
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void shouldFindPublicByRecipeId() {
        List<Cookbook> list = repository.findPublicByRecipeId(2);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void shouldNotFindByMissingRecipeId() {
        List<Cookbook> empty = repository.findAllByRecipeId(999);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldFindByTitle() {
        List<Cookbook> list = repository.findPublicByTitle("Assorted Recipes");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getUserId());
    }

    @Test
    void shouldNotFindByMissingTitle() {
        List<Cookbook> empty = repository.findPublicByTitle("missing title");
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }

    @Test
    void shouldAdd() {
        Cookbook cookbook = new Cookbook();
        cookbook.setCookbookId(0);
        cookbook.setTitle("new title");
        cookbook.setPrivate(true);
        cookbook.setUserId(3);

        Cookbook result = repository.add(cookbook);
        assertNotNull(result);
        assertTrue(result.getCookbookId() >= 3);
    }

    @Test
    void shouldUpdate() {
        Cookbook cookbook = new Cookbook();
        cookbook.setCookbookId(2);
        cookbook.setTitle("updated title");
        cookbook.setPrivate(true);
        cookbook.setUserId(2);

        assertTrue(repository.update(cookbook));
        assertEquals("updated title", repository.findById(2).getTitle());
    }

    @Test
    void shouldNotUpdateMissing() {
        Cookbook missing = new Cookbook();
        missing.setCookbookId(999);
        missing.setTitle("missing title");
        missing.setPrivate(true);
        missing.setUserId(2);

        assertFalse(repository.update(missing));
        assertEquals(0, repository.findPublicByTitle("missing title").size());
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }
}

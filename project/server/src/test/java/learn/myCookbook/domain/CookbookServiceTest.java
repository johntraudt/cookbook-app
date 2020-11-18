package learn.myCookbook.domain;

import learn.myCookbook.data.CookbookRepository;
import learn.myCookbook.models.Cookbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CookbookServiceTest {

    @Autowired
    CookbookService service;

    @MockBean
    CookbookRepository repository;

    @Test
    void shouldNotAddNull() {
        Cookbook cookbook = null;
        Result<Cookbook> result = service.add(cookbook);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidId() {
        Cookbook cookbook = makeCookbook();
        cookbook.setCookbookId(1);
        Result<Cookbook> result = service.add(cookbook);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidTitle() {
        Cookbook cookbook = makeCookbook();
        cookbook.setTitle(null);
        Result<Cookbook> result = service.add(cookbook);
        assertEquals(ResultType.INVALID, result.getType());

        cookbook.setTitle(" ");
        result = service.add(cookbook);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidUser() {
        Cookbook cookbook = makeCookbook();
        cookbook.setUserId(-1);
        Result<Cookbook> result = service.add(cookbook);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(service.deleteById(1));
    }


    Cookbook makeCookbook() {
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Home Cooking");
        cookbook.setPrivate(true);
        cookbook.setUserId(1);
        return cookbook;
    }


}
package learn.myCookbook.domain;

import learn.myCookbook.data.CookbookRepository;
import learn.myCookbook.models.Cookbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CookbookServiceTest {

    @Autowired
    CookbookService service;

    @MockBean
    CookbookRepository repository;

    Cookbook makeCookbook() {
        Cookbook cookbook = new Cookbook();

        return cookbook;
    }


}
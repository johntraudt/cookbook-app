package learn.myCookbook.data;

import learn.myCookbook.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<User> users = repository.findAll();

        assertNotNull(users);
        assertTrue(users.size() == 4);
    }

    @Test
    void shouldFindJohn() {
        User user = repository.findById(1);

        assertEquals(1, user.getUserId());
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldAdd() {
        User user = makeUser();
        User actual = repository.add(user);

        assertNotNull(actual);
        assertEquals(5, actual.getUserId());
    }

    @Test
    void shouldUpdate() {
        User user = makeUser();
        user.setUserId(2);

        assertTrue(repository.update(user));
    }

    @Test
    void shouldNotUpdateMissing() {
        User user = makeUser();
        user.setUserId(200);

        assertFalse(repository.update(user));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }

    private User makeUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testerson");
        user.setEmail("test@email.com");
        user.setUserRoleId(1);
        return user;
    }


}
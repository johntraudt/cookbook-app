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

        for (User user : users) {
            System.out.println(user.getUserId() + user.getFirstName());
        }

        assertTrue(users.size() >= 3);
        assertTrue(users.size() <= 5);
    }

    @Test
    void shouldFindById() {
        User user = repository.findById(1);

        assertEquals(1, user.getUserId());
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldFindByUserName() {
        User user = repository.findByUserName("john.traudt");
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldFindByEmail() {
        User user = repository.findByEmail("john@traudt.com");
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldNotFindByMissing() {
        assertNull(repository.findById(999));
        assertNull(repository.findByEmail("missing"));
        assertNull(repository.findByUserName("missing"));
    }

    @Test
    void shouldVerifyUserNamePassword() {
        assertTrue(repository.correctUserNamePassword("john.traudt", "password"));
    }

    @Test
    void shouldNotVerifyUserNamePassword() {
        assertFalse(repository.correctUserNamePassword("missing", "password"));
        assertFalse(repository.correctUserNamePassword("john.traudt", "missing"));
    }

    @Test
    void shouldAdd() {
        User user = makeUser();
        user.setEmail("new email");
        User actual = repository.add(user);

        assertNotNull(actual);
        System.out.println(actual.getUserId());
        assertTrue(actual.getUserId() >= 4);
        assertTrue(actual.getUserId() <= 5);
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
    void shouldDeactivate() {
        assertTrue(repository.deactivateById(2));
        assertFalse(repository.findById(2).isActive());
    }

    private User makeUser() {
        User user = new User();
        user.setUserId(0);
        user.setFirstName("Test");
        user.setLastName("Testerson");
        user.setEmail("test@email.com");
        user.setUserName("another placeholder");
        user.setPasswordHash("password123");
        user.setUserRoleId(1);
        return user;
    }


}
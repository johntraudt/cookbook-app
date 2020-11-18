package learn.myCookbook.data;

import learn.myCookbook.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<AppUser> users = repository.findAll();

        assertNotNull(users);
        assertTrue(users.size() >= 3);
        assertTrue(users.size() <= 5);
    }

    @Test
    void shouldFindById() {
        AppUser user = repository.findById(1);

        assertEquals(1, user.getUserId());
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldFindByUserName() {
        AppUser user = repository.findByUserName("john.traudt");
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
    }

    @Test
    void shouldFindByEmail() {
        AppUser user = repository.findByEmail("john@traudt.com");
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
        AppUser user = makeUser();
        user.setEmail("new email");
        AppUser actual = repository.add(user);

        assertNotNull(actual);
        assertTrue(actual.getUserId() >= 4);
    }

    @Test
    void shouldUpdate() {
        AppUser user = makeUser();
        user.setUserName("new user name");
        user.setUserId(2);

        assertTrue(repository.update(user));
    }

    @Test
    void shouldNotUpdateMissing() {
        AppUser user = makeUser();
        user.setUserId(200);

        assertFalse(repository.update(user));
    }

    @Test
    void shouldDeactivate() {
        assertTrue(repository.deactivateById(2));
        assertFalse(repository.findById(2).isActive());
    }

    private AppUser makeUser() {
        AppUser user = new AppUser();
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
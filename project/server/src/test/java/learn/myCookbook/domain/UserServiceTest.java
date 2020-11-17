package learn.myCookbook.domain;

import learn.myCookbook.data.AppUserRepository;
import learn.myCookbook.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserRepository appUserRepository;

    @Test
    void shouldNotAddNull() {
        AppUser user = null;
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddEmailTaken() {

    }

    @Test
    void shouldNotAddUsernameTake() {

    }

    @Test
    void shouldNotUpdateWithoutId() {

    }

    private AppUser makeUser() {
        AppUser user = new AppUser();
        user.setFirstName("Test");
        user.setLastName("Testerson");
        user.setEmail("test@test.com");
        user.setActive(true);
        user.setUserName("testTest");
        return user;
    }
}
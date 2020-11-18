package learn.myCookbook.domain;

import learn.myCookbook.App;
import learn.myCookbook.data.AppUserRepository;
import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.AppUser;
import learn.myCookbook.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    RecipeRepository recipeRepository;

    @MockBean
    AppUserRepository repository;

    @Test
    void shouldNotAddNull() {
        AppUser user = null;
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddWithId() {
        AppUser user = makeAppUser();
        user.setUserId(1);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidUsername() {
        AppUser user = makeAppUser();
        user.setUserName(null);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setUserName(" ");
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidEmail() {
        AppUser user = makeAppUser();
        user.setEmail(null);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setEmail(" ");
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidPassword() {
        AppUser user = makeAppUser();
        user.setPasswordHash(null);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setPasswordHash(" ");
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidFirstName() {
        AppUser user = makeAppUser();
        user.setFirstName(null);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setFirstName(" ");
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidLastName() {
        AppUser user = makeAppUser();
        user.setLastName(null);
        Result<AppUser> result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());

        user.setLastName(" ");
        result = service.add(user);
        assertEquals(ResultType.INVALID, result.getType());
    }


    AppUser makeAppUser() {
        AppUser user = new AppUser();
        user.setUserName("username");
        user.setPasswordHash("password");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        user.setEmail("email");
        user.setActive(true);
        user.setUserRoleId(1);
        return user;
    }

}
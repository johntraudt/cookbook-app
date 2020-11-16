package learn.myCookbook.domain;

import learn.myCookbook.data.AppUserRepository;
import learn.myCookbook.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserRepository repository;



    private AppUser makeUser() {
        AppUser user = new AppUser();

        return user;
    }
}
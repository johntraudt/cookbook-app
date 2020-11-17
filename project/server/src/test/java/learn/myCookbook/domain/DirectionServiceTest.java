package learn.myCookbook.domain;

import learn.myCookbook.data.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DirectionServiceTest {

    @Autowired
    DirectionService service;

    @MockBean
    DirectionRepository repository;

    

}
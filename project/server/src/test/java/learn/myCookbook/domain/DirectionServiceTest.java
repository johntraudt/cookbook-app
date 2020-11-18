package learn.myCookbook.domain;

import learn.myCookbook.data.DirectionRepository;
import learn.myCookbook.models.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DirectionServiceTest {

    @Autowired
    DirectionService service;

    @MockBean
    DirectionRepository repository;


    @Test
    void shouldNotAddNull() {
        Direction direction = null;
        Result<Direction> result = service.add(direction);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidRecipeId() {
        Direction direction = makeDirection();
        direction.setRecipeId(-1);
        Result<Direction> result = service.add(direction);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidNumber() {
        Direction direction = makeDirection();
        direction.setDirectionNumber(-1);
        Result<Direction> result = service.add(direction);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotAddInvalidText() {
        Direction direction = makeDirection();
        direction.setText(null);
        Result<Direction> result = service.add(direction);
        assertEquals(ResultType.INVALID, result.getType());

        direction.setText(" ");
        result = service.add(direction);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateExisting() {
        Direction direction = makeDirection();
        direction.setRecipeId(1);

        when(repository.update(direction)).thenReturn(true);
        Result<Direction> actual = service.update(direction);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        Direction direction = makeDirection();
        direction.setRecipeId(100);

        when(repository.update(direction)).thenReturn(true);
        Result<Direction> actual = service.update(direction);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    Direction makeDirection() {
        Direction direction = new Direction();
        direction.setDirectionNumber(1);
        direction.setRecipeId(1);
        direction.setText("Place in the oven.");
        return direction;
    }

}
package learn.myCookbook.data;

import learn.myCookbook.models.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DirectionJdbcTemplateRepositoryTest {

    @Autowired
    DirectionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByRecipeId() {
        Direction direction = repository.findByRecipeId(2);

        assertNotNull(direction);
        assertEquals("Peel 8 potatos", direction.getText());
    }

    @Test
    void shouldNotFindMissing() {
        Direction result = repository.findByRecipeId(500);

        assertNull(result);
    }

    @Test
    void shouldAdd() {
        Direction direction = makeDirection();
        Direction result = repository.add(direction);

        assertNotNull(result);
        assertTrue(result.getDirectionId() == 13);
    }

    @Test
    void shouldUpdate() {
        Direction direction = makeDirection();
        direction.setDirectionId(1);
        assertTrue(repository.update(direction));
    }

    @Test
    void shouldNotUpdateMissing() {
        Direction direction = makeDirection();

        direction.setDirectionId(500);
        assertFalse(repository.update(direction));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(500));
    }

    private Direction makeDirection() {
        Direction direction = new Direction();
        direction.setRecipeId(1);
        direction.setDirectionNumber(4);
        direction.setText("Eat that chicken.");
        return direction;
    }

}
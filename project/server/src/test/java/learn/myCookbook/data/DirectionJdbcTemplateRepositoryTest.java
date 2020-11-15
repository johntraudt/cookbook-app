package learn.myCookbook.data;

import learn.myCookbook.models.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        List<Direction> directions = repository.findByRecipeId(3);

        assertNotNull(directions);
        assertEquals(3, directions.size());
    }

    @Test
    void shouldNotByFindMissingRecipId() {
        List<Direction> result = repository.findByRecipeId(999);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindById() {
        Direction direction = repository.findById(1);
        assertNotNull(direction);
        assertEquals("Buy an entire chicken", direction.getText());
    }

    @Test
    void shouldNotFindByMissingId() {
        Direction direction = repository.findById(999);
        assertNull(direction);
    }

    @Test
    void shouldFindByRecipeIdAndStepNumber() {
        Direction direction = repository.findByRecipeIdAndDirectionNumber(1,2);
        assertNotNull(direction);
        assertEquals("Bake the entire chicken for 20 minutes", direction.getText());
    }

    @Test
    void shouldNotFindByMissingRecipeIdAndStepNumber() {
        Direction direction = repository.findByRecipeIdAndDirectionNumber(1,999);
        assertNull(direction);
    }

    @Test
    void shouldAdd() {
        Direction direction = makeDirection();
        Direction result = repository.add(direction);

        assertNotNull(result);
        assertTrue(result.getDirectionId() >= 12);
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
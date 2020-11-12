package learn.myCookbook.data;

import learn.myCookbook.models.MeasurementUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MeasurementUnitJdbcTemplateRepositoryTest {
    @Autowired
    MeasurementUnitJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<MeasurementUnit> all = repository.findAll();
        assertTrue(all.size() >= 3);
    }

    @Test
    void shouldFindById() {
        MeasurementUnit cup = repository.findById(1);
        assertNotNull(cup);
        assertEquals("CUP", cup.getName());
    }

    @Test
    void shouldNotFindByMissingId() {
        MeasurementUnit missing = repository.findById(999);
        assertNull(missing);
    }

    @Test
    void shouldFindByName() {
        MeasurementUnit cup = repository.findByName("CUP");
        assertNotNull(cup);
        assertEquals(1, cup.getMeasurementUnitId());
    }

    @Test
    void shouldNotFindByMissingName() {
        MeasurementUnit missing = repository.findByName("missing name");
        assertNull(missing);
    }

    @Test
    void shouldAdd() {
        MeasurementUnit quart = new MeasurementUnit(0, "QUART");
        MeasurementUnit result = repository.add(quart);
        assertNotNull(result);
        assertTrue(result.getMeasurementUnitId() > 3);
    }

    @Test
    void shouldUpdate() {
        MeasurementUnit pound = new MeasurementUnit(2, "LB");
        assertTrue(repository.update(pound));
        assertEquals("LB", repository.findById(2).getName());
    }

    @Test
    void shouldNotUpdateMissing() {
        MeasurementUnit missing = new MeasurementUnit(999, "missing");
        assertFalse(repository.update(missing));
        assertNull(repository.findByName("missing"));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(4));
        assertFalse(repository.deleteById(4));
    }
}

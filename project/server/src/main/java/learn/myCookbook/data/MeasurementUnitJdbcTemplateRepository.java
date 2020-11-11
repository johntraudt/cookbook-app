package learn.myCookbook.data;

import learn.myCookbook.data.mappers.MeasurementUnitMapper;
import learn.myCookbook.models.MeasurementUnit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class MeasurementUnitJdbcTemplateRepository implements MeasurementUnitRepository{
    private final JdbcTemplate jdbcTemplate;

    public MeasurementUnitJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MeasurementUnit> findAll() {
        final String sql = "select measurement_unit_id, `name` " +
                "from measurement_unit limit 1000;";

        return jdbcTemplate.query(sql, new MeasurementUnitMapper());
    }

    @Override
    public MeasurementUnit findById(int measurementUnitId) {
        final String sql = "select measurement_unit_id, `name` " +
                "from measurement_unit where measurement_unit_id = ?;";

        return jdbcTemplate.query(sql, new MeasurementUnitMapper(), measurementUnitId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public MeasurementUnit findByName(String name) {
        final String sql = "select measurement_unit_id, `name` " +
                "from measurement_unit where `name` = ?;";

        return jdbcTemplate.query(sql, new MeasurementUnitMapper(), name)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public MeasurementUnit add(MeasurementUnit measurementUnit) {
        final String sql = "insert into measurement_unit (`name`) " +
                "values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, measurementUnit.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        measurementUnit.setMeasurementUnitId(keyHolder.getKey().intValue());
        return measurementUnit;
    }

    @Override
    public boolean update(MeasurementUnit measurementUnit) {
        final String sql = "update measurement_unit set " +
                "`name` = ? " +
                "where measurement_unit_id = ?";
        return jdbcTemplate.update(sql,
                measurementUnit.getName(),
                measurementUnit.getMeasurementUnitId()) > 0;
    }

    @Override
    public boolean deleteById(int measurementUnitId) {
        return jdbcTemplate.update("delete from measurement_unit where measurement_unit_id = ?;", measurementUnitId) > 0;
    }
}

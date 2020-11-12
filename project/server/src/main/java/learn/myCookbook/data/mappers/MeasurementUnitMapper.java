package learn.myCookbook.data.mappers;

import learn.myCookbook.models.MeasurementUnit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeasurementUnitMapper implements RowMapper<MeasurementUnit> {
    @Override
    public MeasurementUnit mapRow(ResultSet resultSet, int i) throws SQLException {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setMeasurementUnitId(resultSet.getInt("measurement_unit_id"));
        measurementUnit.setName(resultSet.getString("mName"));
        return measurementUnit;
    }
}
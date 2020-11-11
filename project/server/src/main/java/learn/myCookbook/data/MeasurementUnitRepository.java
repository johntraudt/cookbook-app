package learn.myCookbook.data;

import learn.myCookbook.models.MeasurementUnit;

import java.util.List;

public interface MeasurementUnitRepository {
    List<MeasurementUnit> findAll();

    MeasurementUnit findById(int measurementUnitId);

    MeasurementUnit findByName(String name);

    MeasurementUnit add(MeasurementUnit measurementUnit);

    boolean update(MeasurementUnit measurementUnit);

    boolean deleteById(int measurementUnitId);
}

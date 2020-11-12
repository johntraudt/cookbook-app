package learn.myCookbook.models;

public class MeasurementUnit {
    private int measurementUnitId;
    private String name;

    public MeasurementUnit() {
    }

    public MeasurementUnit(int measurementUnitId, String name) {
        this.measurementUnitId = measurementUnitId;
        this.name = name;
    }

    public int getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(int measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package learn.myCookbook.domain;

import learn.myCookbook.data.MeasurementUnitRepository;
import learn.myCookbook.models.MeasurementUnit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementUnitService {

    private final MeasurementUnitRepository repository;

    public MeasurementUnitService(MeasurementUnitRepository repository) {
        this.repository = repository;
    }

    public List<MeasurementUnit> findAll() {
        ArrayList<MeasurementUnit> all = (ArrayList<MeasurementUnit>) repository.findAll();
        MeasurementUnit blank = all.stream()
                .filter(mu -> mu.getMeasurementUnitId() == 5)
                .findFirst().orElse(null);

        all.remove(blank);
        all.add(0, blank);
        return all;
    }
}

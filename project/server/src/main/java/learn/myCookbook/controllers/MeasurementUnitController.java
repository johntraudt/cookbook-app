package learn.myCookbook.controllers;

import learn.myCookbook.domain.MeasurementUnitService;
import learn.myCookbook.models.MeasurementUnit;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/measurement-unit")
public class MeasurementUnitController {

    private final MeasurementUnitService service;

    public MeasurementUnitController(MeasurementUnitService service) {
        this.service = service;
    }

    @GetMapping
    public List<MeasurementUnit> findAll() {
        return service.findAll();
    }
}

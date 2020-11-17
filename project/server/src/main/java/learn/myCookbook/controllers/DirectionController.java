package learn.myCookbook.controllers;

import learn.myCookbook.domain.DirectionService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/direction")
public class DirectionController {

    private final DirectionService service;

    public DirectionController(DirectionService service) {
        this.service = service;
    }

    @GetMapping("/{directionId}")
    public Direction findById(@PathVariable int directionId) {
        return service.findById(directionId);
    }

    @GetMapping("/{recipeId}/{directionNumber}")
    public Direction findBYRecipeIdAndDirectionNumber(@PathVariable int recipeId, @PathVariable int directionNumber) {
        return service.findByRecipeIdAndDirectionNumber(recipeId, directionNumber);
    }

    @GetMapping("/recipe/{recipeId}")
    public List<Direction> findByRecipeId(@PathVariable int recipeId) {
        return service.findByRecipeId(recipeId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Direction direction) {
        Result<Direction> result = service.add(direction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{directionId}")
    public ResponseEntity<Object> update(@PathVariable int directionId, @RequestBody Direction direction) {
        if (directionId != direction.getDirectionId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Direction> result = service.update(direction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/decrement/{directionId}")
    public ResponseEntity<Object> decrementDirectionNumber(@PathVariable int directionId, @RequestBody Direction direction) {
        if (directionId != direction.getDirectionId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Direction> result = service.decrementDirectionNumber(direction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{directionId}")
    public ResponseEntity<Void> deleteById(@PathVariable int directionId) {
        if (service.deleteById(directionId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

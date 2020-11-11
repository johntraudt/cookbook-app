package learn.myCookbook.controllers;

import learn.myCookbook.domain.IngredientService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Ingredient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/ingredient")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Ingredient> findAll() {
//        return service.findAll();
//    }

//    @GetMapping("/{ingredientId}")
//    public Ingredient findById(@PathVariable int ingredientId) {
//        return service.findById(ingredientId);
//    }

//    @PostMapping
//    public ResponseEntity<Object> add(@RequestBody Ingredient ingredient) {
//        Result<Ingredient> result = service.add(ingredient);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }

//    @PutMapping("/{ingredientId}")
//    public ResponseEntity<Object> update(@PathVariable int ingredientId, @RequestBody Ingredient ingredient) {
//        if (ingredientId != ingredient.getIngredientId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        Result<Ingredient> result = service.update(ingredient);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return ErrorResponse.build(result);
//    }

//    @DeleteMapping("/{ingredientId}")
//    public ResponseEntity<Void> deleteById(@PathVariable int ingredientId) {
//        if (service.deleteById(ingredientId)) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}

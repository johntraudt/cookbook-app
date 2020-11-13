package learn.myCookbook.controllers;

import learn.myCookbook.domain.DirectionService;
import learn.myCookbook.models.Direction;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/direction")
public class DirectionController {

    private final DirectionService service;

    public DirectionController(DirectionService service) {
        this.service = service;
    }

    @GetMapping("/{recipeId}")
    public Direction findByRecipeId(@PathVariable int recipeId) {
        return service.findByRecipeId(recipeId);
    }

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

package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Recipe> findAll() {
//        return service.findAll();
//    }

//    @GetMapping("/{recipeId}")
//    public Recipe findById(@PathVariable int recipeId) {
//        return service.findById(recipeId);
//    }

//    @PostMapping
//    public ResponseEntity<Object> add(@RequestBody Recipe recipe) {
//        Result<Recipe> result = service.add(recipe);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }

//    @PutMapping("/{recipeId}")
//    public ResponseEntity<Object> update(@PathVariable int recipeId, @RequestBody Recipe recipe) {
//        if (recipeId != recipe.getRecipeId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        Result<Recipe> result = service.update(recipe);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return ErrorResponse.build(result);
//    }

//    @DeleteMapping("/{recipeId}")
//    public ResponseEntity<Void> deleteById(@PathVariable int recipeId) {
//        if (service.deleteById(recipeId)) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
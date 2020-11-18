package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recipe> findAll() {
        return service.findAll();
    }

    @GetMapping("/featured")
    public List<Recipe> findFeatured() {
        return service.findFeatured();
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> findById(@PathVariable int recipeId) {
        Result<Recipe> result = new Result();
        result.setPayload(service.findById(recipeId));
        if (result.getPayload() == null) {
            return new ResponseEntity<Recipe>(result.getPayload(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Recipe>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/search/{recipeName}")
    public List<Recipe> findByName(@PathVariable String recipeName) {
        return service.findByName(recipeName);
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> findByUserId(@PathVariable int userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/random")
    public int findRandomRecipeId() {
        return service.findRandomRecipeId();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody /*@Valid*/ Recipe recipe/*, BindingResult result*/) {
//        service.add(recipe);
//        if (result.hasErrors()) {
//            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        Result<Recipe> result = service.add(recipe);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Object> update(@PathVariable int recipeId, @RequestBody @Valid Recipe recipe, BindingResult result) {
        if (recipeId != recipe.getRecipeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        service.update(recipe);
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteById(@PathVariable int recipeId) {
        if (service.deleteById(recipeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
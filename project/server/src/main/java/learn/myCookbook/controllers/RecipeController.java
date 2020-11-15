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

    @GetMapping("/{recipeId}")
    public Recipe findById(@PathVariable int recipeId) {
        return service.findById(recipeId);
    }

    @GetMapping("/search/{recipeName}")
    public List<Recipe> findByName(@PathVariable String recipeName) {
        return service.findByName(recipeName);
    }

    @GetMapping("/random")
    public Recipe findRandom() {
        return service.findRandom();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Recipe recipe, BindingResult result) {
        service.add(recipe);
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
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
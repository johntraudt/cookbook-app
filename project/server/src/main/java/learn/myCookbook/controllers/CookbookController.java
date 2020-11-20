package learn.myCookbook.controllers;

import learn.myCookbook.domain.CookbookService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Cookbook;
import learn.myCookbook.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cookbook")
public class CookbookController {
    private final CookbookService service;

    public CookbookController(CookbookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cookbook> findAll() {
        return service.findAll();
    }

    @GetMapping("/{cookbookId}")
    public Cookbook findById(@PathVariable int cookbookId) {
        return service.findById(cookbookId);
    }

    @GetMapping("/user/{userId}/all")
    public List<Cookbook> findAllByUserId(@PathVariable int userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping("/user/{userId}/public")
    public List<Cookbook> findPublicByUserId(@PathVariable int userId) {
        return service.findPublicByUserId(userId);
    }

    @GetMapping("/recipe/{recipeId}/all")
    public List<Cookbook> findAllByRecipeId(@PathVariable int recipeId) {
        return service.findAllByRecipeId(recipeId);
    }

    @GetMapping("/recipe/{recipeId}/public")
    public List<Cookbook> findPublicByRecipeId(@PathVariable int recipeId) {
        return service.findPublicByRecipeId(recipeId);
    }

    @GetMapping("/title/{titleId}/public")
    public List<Cookbook> findPublicByTitle(@PathVariable String title) {
        return service.findPublicByTitle(title);
    }

    @GetMapping("/{cookbookId}/recipes")
    public List<Recipe> findRecipesByCookBookId(@PathVariable int cookbookId) {
        return service.findRecipesByCookBookId(cookbookId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Cookbook cookbook) {
        Result<Cookbook> result = service.add(cookbook);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{cookbookId}/{recipeId}")
    public ResponseEntity<Void> insertRecipeById(@PathVariable int cookbookId, @PathVariable int recipeId) {
        if (service.insertRecipeById(cookbookId, recipeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{cookbookId}")
    public ResponseEntity<Object> update(@PathVariable int cookbookId, @RequestBody Cookbook cookbook) {
        if (cookbookId != cookbook.getCookbookId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Cookbook> result = service.update(cookbook);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{cookbookId}")
    public ResponseEntity<Void> deleteById(@PathVariable int cookbookId) {
        if (service.deleteById(cookbookId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{cookbookId}/{recipeId}")
    public ResponseEntity<Void> removeRecipeById(@PathVariable int cookbookId, @PathVariable int recipeId) {
        if (service.removeRecipeById(cookbookId, recipeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
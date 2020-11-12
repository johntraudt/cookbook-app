package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeIngredientService;
import learn.myCookbook.models.RecipeIngredient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/recipe-ingredient")
public class RecipeIngredientController {
    private final RecipeIngredientService service;

    public RecipeIngredientController(RecipeIngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecipeIngredient> findAll() {
        return service.findAll();
    }

    @GetMapping("/{recipeIngredientId}")
    public RecipeIngredient findById(@PathVariable int recipeIngredientId) {
        return service.findById(recipeIngredientId);
    }
}

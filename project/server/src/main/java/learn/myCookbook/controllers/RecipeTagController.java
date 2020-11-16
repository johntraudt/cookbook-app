package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeTagService;
import learn.myCookbook.models.RecipeTag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/recipe-tag")
public class RecipeTagController {

    private final RecipeTagService service;

    public RecipeTagController(RecipeTagService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecipeTag> findAll() {
        return service.findAll();
    }

    @GetMapping("/{recipeTagId}")
    public RecipeTag findById(@PathVariable int recipeTagId) {
        return service.findById(recipeTagId);
    }
}

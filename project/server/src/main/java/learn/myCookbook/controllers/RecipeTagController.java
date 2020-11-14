package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeTagService;
import learn.myCookbook.models.RecipeTag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

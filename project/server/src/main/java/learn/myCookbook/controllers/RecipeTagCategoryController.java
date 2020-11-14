package learn.myCookbook.controllers;

import learn.myCookbook.domain.RecipeTagCategoryService;
import learn.myCookbook.models.RecipeTagCategory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/recipe-tag-category")
public class RecipeTagCategoryController {

    private final RecipeTagCategoryService service;

    public RecipeTagCategoryController(RecipeTagCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecipeTagCategory> findAll() {
        return service.findAll();
    }
}

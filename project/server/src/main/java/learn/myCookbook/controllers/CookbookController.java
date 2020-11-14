package learn.myCookbook.controllers;

import learn.myCookbook.domain.CookbookService;
import learn.myCookbook.domain.Result;
import learn.myCookbook.models.Cookbook;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
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

    @PostMapping
    public Result<Cookbook> add(@RequestBody Cookbook cookbook) {
        return service.add(cookbook);
    }
}
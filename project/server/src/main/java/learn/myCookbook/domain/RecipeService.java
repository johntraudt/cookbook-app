package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeRepository;
import learn.myCookbook.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll() {
        return repository.findAll();
    }

    public Recipe findById(int recipeId) {
        return repository.findById(recipeId);
    }
}

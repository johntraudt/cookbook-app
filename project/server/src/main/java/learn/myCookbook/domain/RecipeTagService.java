package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeTagRepository;
import learn.myCookbook.models.RecipeTag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeTagService {

    private final RecipeTagRepository repository;

    public RecipeTagService(RecipeTagRepository repository) {
        this.repository = repository;
    }

    public List<RecipeTag> findAll() {
        return repository.findAll();
    }

    public Result<RecipeTag> tagRecipe(int recipeId, int recipeTagId) {
        Result<RecipeTag> result = new Result<>();

        if (repository.recipeAlreadyTagged(recipeId, recipeTagId)) {
            result.addMessage("That recipe has already been tagged with " + repository.findById(recipeTagId).getName(), ResultType.INVALID);
        }
        repository.tagRecipe(recipeId, recipeTagId);

        return result;
    }
}

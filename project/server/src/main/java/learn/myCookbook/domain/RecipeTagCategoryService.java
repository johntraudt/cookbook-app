package learn.myCookbook.domain;

import learn.myCookbook.data.RecipeTagCategoryRepository;
import learn.myCookbook.models.RecipeTagCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeTagCategoryService {

    private final RecipeTagCategoryRepository repository;

    public RecipeTagCategoryService(RecipeTagCategoryRepository repository) {
        this.repository = repository;
    }

    public List<RecipeTagCategory> findAll() {
        return repository.findAll();
    }
}

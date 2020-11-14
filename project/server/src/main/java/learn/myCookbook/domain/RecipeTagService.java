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
}

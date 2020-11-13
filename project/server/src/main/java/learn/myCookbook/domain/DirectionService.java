package learn.myCookbook.domain;

import learn.myCookbook.data.DirectionRepository;
import learn.myCookbook.models.Direction;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    private final DirectionRepository repository;

    public DirectionService(DirectionRepository repository) {
        this.repository = repository;
    }

    public Direction findByRecipeId(int recipeId) {
        return repository.findByRecipeId(recipeId);
    }


}

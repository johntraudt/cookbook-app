package learn.myCookbook.domain;

import learn.myCookbook.data.IngredientRepository;
import learn.myCookbook.models.Ingredient;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> findAll() {
        return repository.findAll();
    }

    public Ingredient findById(int ingredientId) {
        return repository.findById(ingredientId);
    }

    public Ingredient findByName(String name) {
        return repository.findByName(name);
    }

    /*
        Ingredient add(Ingredient ingredient);

    */
}

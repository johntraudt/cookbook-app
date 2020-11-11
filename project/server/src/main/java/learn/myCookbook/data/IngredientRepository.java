package learn.myCookbook.data;

import learn.myCookbook.models.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> findAll();

    Ingredient findById();

    Ingredient findByName();

    Ingredient add(Ingredient ingredient);

    boolean update(Ingredient ingredient);

    boolean deleteById(Ingredient ingredient);
}

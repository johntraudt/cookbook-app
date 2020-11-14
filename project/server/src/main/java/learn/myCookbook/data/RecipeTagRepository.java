package learn.myCookbook.data;

import learn.myCookbook.models.RecipeTag;

import java.util.List;

public interface RecipeTagRepository {
    List<learn.myCookbook.models.RecipeTag> findAll();

    RecipeTag findById(int recipeTagId);

    RecipeTag findByName(String name);

    RecipeTag add(RecipeTag recipeTag);

    boolean update(RecipeTag recipeTag);

    boolean deleteById(int recipeTagId);
}

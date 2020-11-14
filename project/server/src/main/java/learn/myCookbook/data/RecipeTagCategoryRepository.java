package learn.myCookbook.data;

import learn.myCookbook.models.RecipeTag;
import learn.myCookbook.models.RecipeTagCategory;

import java.util.List;

public interface RecipeTagCategoryRepository {

    List<RecipeTagCategory> findAll();

    RecipeTagCategory findById();

    RecipeTagCategory findByName();

    RecipeTagCategory add(RecipeTagCategory recipeTagCategory);

    boolean update(RecipeTagCategory recipeTagCategory);

    boolean deleteById(int recipeTageCategoryId);
}

package learn.myCookbook.data;

import learn.myCookbook.models.Cookbook;
import learn.myCookbook.models.Recipe;

import java.util.List;

public interface CookbookRepository {

    List<Cookbook> findAll();

    Cookbook findById(int cookbookId);

    List<Cookbook> findAllByUserId(int userId);

    List<Cookbook> findPublicByUserId(int userId);

    List<Cookbook> findAllByRecipeId(int recipeId);

    List<Cookbook> findPublicByRecipeId(int recipeId);

    List<Cookbook> findPublicByTitle(String title);

    List<Recipe> findRecipesByCookBookId(int cookbookId);

    boolean titleTakenForUser(int userId, String title);

    boolean recipeIsInCookbook(int cookbookId, int recipeId);

    Cookbook add(Cookbook cookbook);

    boolean insertRecipeById(int cookBookId, int recipeId);

    boolean update(Cookbook cookbook);

    boolean deleteById(int cookbookId);

    boolean removeRecipeById(int cookBookId, int recipeId);
}

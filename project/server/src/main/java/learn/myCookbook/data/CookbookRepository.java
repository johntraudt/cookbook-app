package learn.myCookbook.data;

import learn.myCookbook.models.Cookbook;

import java.util.List;

public interface CookbookRepository {

    List<Cookbook> findAll();

    Cookbook findById(int cookbookId);

    List<Cookbook> findAllByUserId(int userId);

    List<Cookbook> findPublicByUserId(int userId);

    List<Cookbook> findAllByRecipeId(int recipeId);

    List<Cookbook> findPublicByRecipeId(int recipeId);

    List<Cookbook> findPublicByTitle(String title);

    Cookbook add(Cookbook cookbook);

    boolean update(Cookbook cookbook);

    boolean deleteById(int cookbookId);
}

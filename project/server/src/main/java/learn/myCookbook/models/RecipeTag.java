package learn.myCookbook.models;

public class RecipeTag {

    private int recipeTagId;
    private String name;
    private int recipeTagCategoryId;

    public RecipeTag() {
    }

    public int getRecipeTagId() {
        return recipeTagId;
    }

    public void setRecipeTagId(int recipeTagId) {
        this.recipeTagId = recipeTagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipeTagCategoryId() {
        return recipeTagCategoryId;
    }

    public void setRecipeTagCategoryId(int recipeTagCategoryId) {
        this.recipeTagCategoryId = recipeTagCategoryId;
    }
}

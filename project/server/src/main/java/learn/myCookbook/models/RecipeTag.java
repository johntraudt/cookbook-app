package learn.myCookbook.models;

public class RecipeTag {

    private int recipeTagId;
    private String name;
    private String imageLink;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getRecipeTagCategoryId() {
        return recipeTagCategoryId;
    }

    public void setRecipeTagCategoryId(int recipeTagCategoryId) {
        this.recipeTagCategoryId = recipeTagCategoryId;
    }
}

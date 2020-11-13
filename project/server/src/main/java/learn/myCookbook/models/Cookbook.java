package learn.myCookbook.models;

import java.util.List;

public class Cookbook {
    private int cookbookId;
    private String title;
    private boolean isPrivate;
    private int userId;
    private User user;
    private List<Recipe> recipes;

    public int getCookbookId() {
        return cookbookId;
    }

    public void setCookbookId(int cookbookId) {
        this.cookbookId = cookbookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}

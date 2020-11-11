package learn.myCookbook.models;

import learn.myCookbook.models.enumerations.RecipeTag;

import java.time.LocalDate;
import java.util.List;

public class Recipe {
    private int recipeId;
    private int userId;
    private User user;
    private String name;
    private int prepTimeInMinutes;
    private int cookTimeInMinutes;
    private int servings;
    private LocalDate date;
    private boolean wasUpdated;
    private String imageLink;
    private List<RecipeIngredient> ingredients;
    private List<String> directions;
    private List<RecipeTag> tags;
    private List<Review> reviews;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepTimeInMinutes() {
        return prepTimeInMinutes;
    }

    public void setPrepTimeInMinutes(int prepTimeInMinutes) {
        this.prepTimeInMinutes = prepTimeInMinutes;
    }

    public int getCookTimeInMinutes() {
        return cookTimeInMinutes;
    }

    public void setCookTimeInMinutes(int cookTimeInMinutes) {
        this.cookTimeInMinutes = cookTimeInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isWasUpdated() {
        return wasUpdated;
    }

    public void setWasUpdated(boolean wasUpdated) {
        this.wasUpdated = wasUpdated;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public List<RecipeTag> getTags() {
        return tags;
    }

    public void setTags(List<RecipeTag> tags) {
        this.tags = tags;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

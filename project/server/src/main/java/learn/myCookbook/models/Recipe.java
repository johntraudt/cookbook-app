package learn.myCookbook.models;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class Recipe {
    private int recipeId;

    @NotNull(message = "Recipe must have a user.")
    private int userId;
    private AppUser user;

    @NotNull
    @NotBlank(message = "Recipe must have a name.")
    private String name;

    @Min(value = 0, message = "Recipe must have 0 or greater prep time.")
    private int prepTimeInMinutes;

    @Min(value = 0, message = "Recipe must have 0 or greater cook time.")
    private int cookTimeInMinutes;

    @Min(value = 1, message = "Recipe must have at least 1 serving.")
    private int servings;

    @NotNull(message = "Recipe must have a date.")
    private LocalDate date;

    @NotNull(message = "Recipe must have wasUpdated set.")
    private boolean wasUpdated;

    @NotNull(message = "Recipe must have isFeatured set.")
    private boolean isFeatured;

    private int calories;

    @NotNull
    @NotBlank(message = "Recipe must have link to an image.")
    private String imageLink;

    private List<RecipeIngredient> ingredients;
    private List<Direction> directions;
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

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
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

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
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

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
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

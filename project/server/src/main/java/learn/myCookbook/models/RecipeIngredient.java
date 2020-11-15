package learn.myCookbook.models;

import javax.validation.constraints.Min;

public class RecipeIngredient {
    @Min(value = 0, message = "recipeIngredientId cannot be negative.")
    private int recipeIngredientId;

    @Min(value = 0, message = "recipeId cannot be negative.")
    private int recipeId;

    @Min(value = 0, message = "ingredientId cannot be negative.")
    private int ingredientId;
    private Recipe recipe;
    private Ingredient ingredient;

    @Min(value = 0, message = "ingredientListIndex cannot be negative.")
    private int ingredientListIndex;
    private String quantity;

    @Min(value = 0, message = "measurementUnitId cannot be negative.")
    private int measurementUnitId;
    private MeasurementUnit measurementUnit;



    public int getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(int recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getIngredientListIndex() {
        return ingredientListIndex;
    }

    public void setIngredientListIndex(int ingredientListIndex) {
        this.ingredientListIndex = ingredientListIndex;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(int measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
package learn.myCookbook.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Ingredient {

    @Min(value = 0, message = "IngredientId cannot be negative.")
    private int ingredientId;

    @NotNull(message = "Ingredient name is required.")
    @NotBlank(message = "Ingredient name is required.")
    private String name;

    public Ingredient() {
    }

    public Ingredient(int ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

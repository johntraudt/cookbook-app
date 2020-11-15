package learn.myCookbook.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Direction {

    @Min(value = 0, message = "Direction ID cannot be negative.")
    private int directionId;

    @Min(value = 0, message = "Recipe ID cannot be negative.")
    private int recipeId;

    @Min(value = 0, message = "Recipe ID cannot be negative.")
    private int directionNumber;

    @NotNull(message = "Text is required.")
    @NotBlank(message = "Text is required.")
    private String text;




    public int getDirectionId() {
        return directionId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getDirectionNumber() {
        return directionNumber;
    }

    public void setDirectionNumber(int directionNumber) {
        this.directionNumber = directionNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

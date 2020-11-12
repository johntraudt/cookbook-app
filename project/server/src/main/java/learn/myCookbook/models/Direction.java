package learn.myCookbook.models;

public class Direction {

    private int direction_id;
    private int recipe_id;
    private int direction_number;
    private String text;

    public int getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(int direction_id) {
        this.direction_id = direction_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getDirection_number() {
        return direction_number;
    }

    public void setDirection_number(int direction_number) {
        this.direction_number = direction_number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

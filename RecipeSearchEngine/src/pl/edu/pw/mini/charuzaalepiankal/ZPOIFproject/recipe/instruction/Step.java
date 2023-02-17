package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;

import java.util.List;

public class Step {
    /**
     * Contains information about a single step in Instruction
     **/
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("equipment")
    @Expose
    private List<Equipment> equipment = null;
    @SerializedName("step")
    @Expose
    private String step;

    @Override
    public String toString() {
        return number +
                ". step: " + step;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}

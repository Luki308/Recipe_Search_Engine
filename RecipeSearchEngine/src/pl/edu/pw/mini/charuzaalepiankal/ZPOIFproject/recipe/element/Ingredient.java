package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredient extends Element {
    /**
     * Contains information about a single ingredient.
     **/

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("nutrients")
    @Expose
    private List<Nutrient> nutrients;

    public Ingredient(String name, Double amount, String unit, Integer id, String image) {
        super(name, amount, unit);
        this.id = id;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                ", nutrients=" + nutrients +
                '}';
    }
}

package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.nutrition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Element;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Nutrient;

import java.util.List;

public class Nutrition {

    @SerializedName("caloricBreakdown")
    @Expose
    private NutritionBreakdown breakdown;

    @SerializedName("weightPerServing")
    @Expose
    private Element weightPerServing;

    @SerializedName("nutrients")
    @Expose
    private List<Nutrient> nutrients;

    public Nutrition(NutritionBreakdown breakdown, Element weightPerServing, List<Nutrient> nutrients) {
        this.breakdown = breakdown;
        this.weightPerServing = weightPerServing;
        this.weightPerServing.setName("weight");
        this.nutrients = nutrients;
    }

    public NutritionBreakdown getBreakdown() {
        return breakdown;
    }

    public Element getWeightPerServing() {
        return weightPerServing;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    @Override
    public String toString() {
        return "Nutrition{" +
                "breakdown=" + breakdown +
                ", weightPerServing=" + weightPerServing +
                ", nutrients=" + nutrients +
                '}';
    }
}

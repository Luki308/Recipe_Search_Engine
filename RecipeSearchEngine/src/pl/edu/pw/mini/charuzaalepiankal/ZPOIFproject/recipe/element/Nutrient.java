package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element;

import com.google.gson.annotations.SerializedName;

public class Nutrient extends Element{

    @SerializedName("percentOfDailyNeeds")
    private Double percentOfDailyNeeds;

    public Nutrient(String name, Double amount, String unit, Double percentOfDailyNeeds) {
        super(name, amount, unit);
        this.percentOfDailyNeeds = percentOfDailyNeeds;
    }

    @Override
    public String toString() {
        String s = name + ": " +
                amount +
                unit + ',' +
                "Daily need=" + percentOfDailyNeeds + "%";
        return s;
    }
}

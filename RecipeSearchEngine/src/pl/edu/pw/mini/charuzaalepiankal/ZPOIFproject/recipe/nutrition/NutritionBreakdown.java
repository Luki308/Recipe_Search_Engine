package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.nutrition;

public class NutritionBreakdown {
    private Double percentCarbs;
    private Double percentProtein;
    private Double percentFat;

    public NutritionBreakdown(Double percentCarbs, Double percentProtein, Double percentFat) {
        this.percentCarbs = percentCarbs;
        this.percentProtein = percentProtein;
        this.percentFat = percentFat;
    }

    @Override
    public String toString() {
        String s = "Carbs=" + percentCarbs + "%\n" +
                "Protein=" + percentProtein + "%\n" +
                "Fat=" + percentFat + "%";
        return s;
    }
}

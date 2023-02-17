package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction.Instructions;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.nutrition.Nutrition;

import java.util.List;



public class Recipe {
/**
 * Contains (most) recipe data from API.
 **/
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("readyInMinutes")
    @Expose
    private Integer readyInMinutes;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("spoonacularSourceUrl")
    @Expose
    private String spoonacularSourceUrl;
    @SerializedName("aggregateLikes")
    @Expose
    private Integer aggregateLikes;
    @SerializedName("healthScore")
    @Expose
    private Double healthScore;
    @SerializedName("spoonacularScore")
    @Expose
    private Double spoonacularScore;
    @SerializedName("pricePerServing")
    @Expose
    private Double pricePerServing;
    @SerializedName("analyzedInstructions")
    @Expose
    private List<Instructions> analyzedInstructions = null;
    @SerializedName("cheap")
    @Expose
    private Boolean cheap;
    @SerializedName("cuisines")
    @Expose
    private List<String> cuisines = null;
    @SerializedName("dairyFree")
    @Expose
    private Boolean dairyFree;
    @SerializedName("diets")
    @Expose
    private List<String> diets = null;
    @SerializedName("glutenFree")
    @Expose
    private Boolean glutenFree;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("ketogenic")
    @Expose
    private Boolean ketogenic;
    @SerializedName("occasions")
    @Expose
    private List<String> occasions = null;
    @SerializedName("sustainable")
    @Expose
    private Boolean sustainable;
    @SerializedName("vegan")
    @Expose
    private Boolean vegan;
    @SerializedName("vegetarian")
    @Expose
    private Boolean vegetarian;
    @SerializedName("veryHealthy")
    @Expose
    private Boolean veryHealthy;
    @SerializedName("veryPopular")
    @Expose
    private Boolean veryPopular;
    @SerializedName("dishTypes")
    @Expose
    private List<String> dishTypes = null;
    @SerializedName("extendedIngredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("nutrition")
    @Expose
    private Nutrition nutrition;

    public Recipe(Integer id, String title, String image, String imageType, Integer servings, Integer readyInMinutes, String sourceName, String sourceUrl, String spoonacularSourceUrl, Integer aggregateLikes, Double healthScore, Double spoonacularScore, Double pricePerServing, List<Instructions> analyzedInstructions, Boolean cheap, List<String> cuisines, Boolean dairyFree, List<String> diets, Boolean glutenFree, String instructions, Boolean ketogenic, List<String> occasions, Boolean sustainable, Boolean vegan, Boolean vegetarian, Boolean veryHealthy, Boolean veryPopular, List<String> dishTypes, List<Ingredient> ingredients, String summary, Nutrition nutrition) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.spoonacularSourceUrl = spoonacularSourceUrl;
        this.aggregateLikes = aggregateLikes;
        this.healthScore = healthScore;
        this.spoonacularScore = spoonacularScore;
        this.pricePerServing = pricePerServing;
        this.analyzedInstructions = analyzedInstructions;
        this.cheap = cheap;
        this.cuisines = cuisines;
        this.dairyFree = dairyFree;
        this.diets = diets;
        this.glutenFree = glutenFree;
        this.instructions = instructions;
        this.ketogenic = ketogenic;
        this.occasions = occasions;
        this.sustainable = sustainable;
        this.vegan = vegan;
        this.vegetarian = vegetarian;
        this.veryHealthy = veryHealthy;
        this.veryPopular = veryPopular;
        this.dishTypes = dishTypes;
        this.ingredients = ingredients;
        this.summary = summary;
        this.nutrition = nutrition;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Integer getServings() {
        return servings;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public Double getPricePerServing() {
        return pricePerServing;
    }

    public List<Instructions> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", imageType='" + imageType + '\'' +
                ", servings=" + servings +
                ", readyInMinutes=" + readyInMinutes +
                ", sourceName='" + sourceName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", spoonacularSourceUrl='" + spoonacularSourceUrl + '\'' +
                ", aggregateLikes=" + aggregateLikes +
                ", healthScore=" + healthScore +
                ", spoonacularScore=" + spoonacularScore +
                ", pricePerServing=" + pricePerServing +
                ", analyzedInstructions=" + analyzedInstructions +
                ", cheap=" + cheap +
                ", cuisines=" + cuisines +
                ", dairyFree=" + dairyFree +
                ", diets=" + diets +
                ", glutenFree=" + glutenFree +
                ", instructions='" + instructions + '\'' +
                ", ketogenic=" + ketogenic +
                ", occasions=" + occasions +
                ", sustainable=" + sustainable +
                ", vegan=" + vegan +
                ", vegetarian=" + vegetarian +
                ", veryHealthy=" + veryHealthy +
                ", veryPopular=" + veryPopular +
                ", dishTypes=" + dishTypes +
                ", ingredients=" + ingredients +
                ", summary='" + summary + '\'' +
                ", nutrition='" + nutrition + '\'' +
                '}';
    }
}

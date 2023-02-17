package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Result {
    /**
     * class for scrapping information from json given by APIDataRetriever
     */
    @SerializedName("results")
    private Recipe[] recipe;

    public Result(Recipe[] recipe) {
        this.recipe = recipe;
    }
    

    public Recipe[] getRecipe() {
        return recipe;
    }
}

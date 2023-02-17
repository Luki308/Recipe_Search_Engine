package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;

public class ResultByIngredients {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("missedIngredients")
    @Expose
    private List<Ingredient> missedIngredients = null;
    @SerializedName("image")
    @Expose
    String image;

    public ResultByIngredients(Integer id, String image, List<Ingredient> missedIngredients) {
        this.id = id;
        this.image = image;
        this.missedIngredients = missedIngredients;
    }

    public String getImage() {
        return image;
    }

    public Integer getId() {
        return id;
    }

	public List<Ingredient> getIngredients() {
		return missedIngredients;
	}
	
	
    
    
}

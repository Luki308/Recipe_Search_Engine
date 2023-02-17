package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Equipment {
    /**
     * Contains data about equipment needed in a single step.
     **/
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("localizedName")
    @Expose
    private String localizedName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Equipment(String image, String localizedName, String name, Integer id) {
        this.image = image;
        this.localizedName = localizedName;
        this.name = name;
        this.id = id;
    }
}

package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.instruction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Instructions {
    /**
     * Used for list of instructions in Recipe class
     **/
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("steps")
        @Expose
        public List<Step> steps = null;

    public Instructions(String name, List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }
}

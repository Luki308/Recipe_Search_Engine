package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;

public abstract class IngredientHolder {
	protected List<String> ingredients;
	
	public IngredientHolder() {
		ingredients = new ArrayList<String>();
	}
	
	public void addIngredient(String ingredient) {
		ingredients.add(ingredient);
	}
	
	public String removeIngredient(String ingredient) {
		ingredients.remove(ingredient);
		return ingredient;
	}
	
	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public class XMLSaver {
		public static void saveToFile(IngredientHolder holder, String fileName) throws IOException {
			XStream xstream = new XStream();
			
			FileWriter writer = new FileWriter(fileName);
			xstream.toXML(holder, writer);
			writer.close();
		}
		
		public static IngredientHolder readFromFile(String fileName) {
			XStream xstream = new XStream();
			xstream.addPermission(AnyTypePermission.ANY);
			IngredientHolder holder = (IngredientHolder) xstream.fromXML(new File(fileName));
			return holder;
		}
	}
	
	
	
}

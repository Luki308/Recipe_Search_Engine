package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.element.Ingredient;

public class ShoppingList extends IngredientHolder {
	private Map<String, Double> costs;
	
	public ShoppingList() {
		super();
		costs = new HashMap<String, Double>();
	}
	

	@Override
	public void addIngredient(String ingredient) {
		super.addIngredient(ingredient);
		costs.put(ingredient, null);
	}
	
	
	@Override
	public String removeIngredient(String ingredient) {
		super.removeIngredient(ingredient);
		costs.remove(ingredient);
		return ingredient;
	}


	public void addIngredientWithCostInfo(String ingredient, double cost) {
		costs.put(ingredient, cost);
	}



	public void addMissedIngredients(List<Ingredient> missed) {
		for(Ingredient ingredient : missed) {
			addIngredient(ingredient.getName());
		}
	}


	public Map<String, Double> getCosts() {
		return costs;
	}


	public void setCosts(Map<String, Double> costs) {
		this.costs = costs;
	}
	
	
	
}

package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fridge extends IngredientHolder {
	private Map<String, LocalDate> expirationDates;
	
	public Fridge() {
		super();
		expirationDates = new HashMap<String, LocalDate>();
	}
	

	@Override
	public void addIngredient(String ingredient) {
		super.addIngredient(ingredient);
		expirationDates.put(ingredient, null);
	}


	public void addIngredientWithExpirationDate(String ingredient, String expiration) {
		this.addIngredient(ingredient);
		LocalDate expirationDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			expirationDate = LocalDate.parse(expiration, formatter);
		} catch(DateTimeParseException e) {
			expirationDate = null;
			System.out.println("onie");
		}
		
		expirationDates.put(ingredient, expirationDate);
	}

	@Override
	public String removeIngredient(String ingredient) {
		super.removeIngredient(ingredient);
		expirationDates.remove(ingredient);
		return ingredient;
	}
	
	public List<String> removeExpiredItems() {
		List<String> expired = ingredients.stream()
				.filter(t -> expirationDates.get(t) != null)
				.filter(t -> expirationDates.get(t).isBefore(LocalDate.now()))
				.toList();
		
		ingredients.removeAll(expired);
		expirationDates.keySet().removeAll(expired);
				
		return expired;
	}
	
	public Map<String, LocalDate> getExpirationDates() {
		return expirationDates;
	}


	public void setExpirationDates(Map<String, LocalDate> expirationDates) {
		this.expirationDates = expirationDates;
	}

	
	
	
	
}


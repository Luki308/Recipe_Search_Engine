package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.Result;
import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.recipe.ResultByIngredients;

import java.util.List;

public class APIDataRetriever {
	private static String host = "https://api.spoonacular.com/recipes/";
	private static String key = "69385f4d60214c4c87ea9ee77d638e21";
	
	/**
	 * 
	 * @param keyWord
	 * @return 
	 */
	public static String searchByKeyWord(String keyWord, int number, boolean nutrition) {
		// Returning 1 recipe
		keyWord = keyWord.replace(" ", "+");
		String query = "apiKey=" + key + "&query=" + keyWord + "&addRecipeInformation=true" + "&number=" + Integer.toString(number) + "&addRecipeNutrition=" + Boolean.toString(nutrition);
		HttpResponse<JsonNode> response = null;
		
		try {
			response = Unirest.get(host + "complexSearch?" + query).asJson();

		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String result = gson.toJson(je);

		return result;
	}
	
	public static ResultByIngredients[] searchByIngredients(List<String> ingredients, int number, boolean nutrition) {
		// Costs 2.1 + 0.01 x number of recipes returned
		String names = String.join(",+", ingredients);
		
		String query = "apiKey=" + key + "&ingredients=" + names + "&number=" + Integer.toString(number);
		HttpResponse<JsonNode> response = null;
		
		try {
			// Costs 1.0 + 0.01 per recipe returned
			response = Unirest.get(host + "findByIngredients?" + query).asJson();

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String json = gson.toJson(je);

		Gson gson2 = new GsonBuilder().serializeNulls().create();
		ResultByIngredients[] resultSearch = gson2.fromJson(json, ResultByIngredients[].class);

		return resultSearch;
	}

	public static String getInformation(int id, boolean nutrition){
		// Costs 1.0 + 0.1 = 1.1 points
		String query = "apiKey=" + key  + "&includeNutrition=" + Boolean.toString(nutrition);
		HttpResponse<JsonNode> response = null;
		String searchId = Integer.toString(id);
		try {
			response = Unirest.get(host + searchId + "/information?" + query).asJson();

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String result = gson.toJson(je);

		return result;
	}
	
	public static Result formatResult(String json) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		Result result = gson.fromJson(json, Result.class);
		return result;
	}

}

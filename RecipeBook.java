package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

public class RecipeBook {
	
	private ArrayList<Recipe> recipeList;

	public RecipeBook() {
		recipeList = new ArrayList<Recipe>();
		Gson myGson = new Gson();
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader("src/com/cannon/basegame/data/recipes.json")));
			while(reader.hasNext()) {
				recipeList.add(myGson.fromJson(reader.next(),Recipe.class));
			}
			System.out.println(myGson.toJson(recipeList));
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override 
	public String toString() {
		String returnString = "";
		for(Recipe recipe : recipeList) {
			returnString +=recipe.toString() + " \n";
		}
		return returnString;
	}

	public ArrayList<Recipe> getRecipesById(int id) {
		ArrayList<Recipe> returnList = new ArrayList<Recipe>();
		for(Recipe recipe : recipeList) {
			if(id == recipe.itemId) {
				returnList.add(recipe);
			}
		}
		return returnList;
	}
	
	public Item getItemFromIngredients(int id1, int id2, int id3) {
		for(Recipe recipe : recipeList) {
			if(recipe.checkIngredients(id1, id2, id3)) {
				return new Item(recipe);
			}
		}
		
		return null;
	}
	

}

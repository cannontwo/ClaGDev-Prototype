package com.cannon.basegame;


public class Recipe {
	
	private int ingred1;
	private int ingred2;
	private int ingred3;
	public int itemId;
	private String imageLocation;

	public Recipe() {
		ingred1 = 0;
		ingred2 = 0;
		ingred3 = 1;
		itemId = 0;
		imageLocation = "fang.png";
	}
	
	public Recipe(int ingred1,int ingred2,int ingred3,int itemId,String imageLocation) {
		this.ingred1 = ingred1;
		this.ingred2 = ingred2;
		this.ingred3 = ingred3;
		this.itemId = itemId;
		this.imageLocation = imageLocation;
	}
	
	@Override
	public String toString() {
		return "Recipe " + itemId + ": " + ingred1 + " , " + ingred2 + " , " + ingred3 + " , " + imageLocation;
	}

	public String getImageLocation() {
		return imageLocation;
	}
	
	public boolean checkIngredients(int id1, int id2, int id3) {
		boolean hasIngred1, hasIngred2, hasIngred3;
		hasIngred1 = hasIngred2 = hasIngred3 = false;
		int[] possibleIngreds = {id1, id2, id3};
		
		if(ingred1 == 99) {
			hasIngred1 = true;
		} else if(ingred2 == 99) {
			hasIngred2 = true;
		} else if(ingred3 == 99) {
			hasIngred3 = true;
		}
		
		
		for(Integer id : possibleIngreds) {
			if(ingred1 == id && !hasIngred1) {
				hasIngred1 = true;
				continue;
			}
			
			if(ingred2 == id && !hasIngred2) {
				hasIngred2 = true;
				continue;
			}
			
			if(ingred3 == id && !hasIngred3) {
				hasIngred3 = true;
				continue;
			}
		}
		
		if(hasIngred1 == hasIngred2 == hasIngred3 == true) {
			return true;
		} else {
			return false;
		}
	}
}

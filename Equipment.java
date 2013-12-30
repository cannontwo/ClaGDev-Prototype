package com.cannon.basegame;

import java.util.HashMap;

public class Equipment extends Item {

	public static final int WEAPON = 0;
	public static final int MATERIAL = 1;
	public static final int COLOR = 2;
	public static final int SUSPENSION = 3;
	
	private HashMap<String,Integer> stats;
	private int equipmentType;
	private Action actionType = null; //need to set it
	
	public Equipment() {
		this(0);
	}

	public Equipment(Recipe recipe) {
		this(recipe.itemId);
		// TODO Auto-generated constructor stub
	}

	public Equipment(int id) {
		super(id);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}

	public Equipment(int id, int x, int y) {
		super(id, x, y);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}
	
	public int getEquipmentType(){
		return equipmentType;
	}
	
	public HashMap<String, Integer> getStats(){
		return stats;
	}
	
	public Action getAction(){
		return actionType;
	}

}

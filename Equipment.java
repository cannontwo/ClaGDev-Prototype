package com.cannon.basegame;

import java.util.HashMap;


public class Equipment extends Item {
	
	public static final int WEAPON = 0;
	public static final int MATERIAL = 1;
	public static final int COLOR = 2;
	public static final int SUSPENSION = 3;
	
	private HashMap<String,Integer> stats;
	private int equipmentType;
	
	public Equipment() {
		super(0);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}
	
	public Equipment(Recipe recipe) {
		super(recipe);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}
	
	public Equipment(int id) {
		super(id);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}
	
	public Equipment(int id, int x, int y) {
		super(id,x,y);
		this.stats = Statistics.getEquipmentStats(id);
		equipmentType = stats.get("EquipmentType");
	}
	
	public HashMap<String,Integer> getStats() {
		return stats;
	}
	
	public int getEquipmentType() {
		return equipmentType;
	}
}

package com.cannon.basegame;

import java.util.HashMap;


public class Equipment extends Item {
	
	private HashMap<String,Integer> stats;
	
	public Equipment() {
		super(0);
		this.stats = Statistics.getEquipmentStats(id);
	}
	
	public Equipment(Recipe recipe) {
		super(recipe);
		this.stats = Statistics.getEquipmentStats(id);
	}
	
	public Equipment(int id) {
		super(id);
		this.stats = Statistics.getEquipmentStats(id);
	}
	
	public Equipment(int id, int x, int y) {
		super(id,x,y);
		this.stats = Statistics.getEquipmentStats(id);
	}
	
	public HashMap<String,Integer> getStats() {
		return stats;
	}
}

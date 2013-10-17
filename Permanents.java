package com.cannon.basegame;

public class Permanents {
	private static Inventory inventory;
	
	public static Inventory getInventory() {
		return inventory;
	}
	
	public static void setInventory(Inventory newInventory) {
		inventory = newInventory;
	}
}

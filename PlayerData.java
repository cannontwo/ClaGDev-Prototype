package com.cannon.basegame;

public class PlayerData {
	
	private float x;
	private float y;
	private int[] inventory;
	private int health;

	public PlayerData(Player player) {
		this.x = player.getX();
		this.y = player.getY();
		this.inventory = player.getInventoryIdArray();
		this.health = player.getHealth();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int[] getInventoryIdArray() {
		return inventory;
	}

	public void setInventoryIdArray(int[] inventory) {
		this.inventory = inventory;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	

}

package com.cannon.basegame;

public class EntityData {
	
	private float x;
	private float y;
	private int[] inventory;
	private int health;
	private int entityType;
	private int id;
	

	public EntityData(Entity entity) {
		this.x = entity.getX();
		this.y = entity.getY();
		this.health = entity.getHealth();
		if(entity instanceof Player) {
			this.inventory = ((Player)(entity)).getInventoryIdArray();
			this.entityType = EntityData.PLAYER;
		} else if(entity instanceof Item) {
			this.id = ((Item)(entity)).getId();
			this.entityType = EntityData.ITEM;
		} else if(entity instanceof MeleeEnemy) {
			this.id = entity.getId();
			this.entityType = EntityData.MELEE_ENEMY;
		}
		else {
			this.entityType = EntityData.OTHER;
		}
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
	
	public int getEntityType() {
		return this.entityType;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static final int PLAYER = 0;
	public static final int ITEM = 1;
	public static final int MELEE_ENEMY = 3;
	public static final int OTHER = 99;

}

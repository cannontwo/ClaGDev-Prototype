package com.cannon.basegame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Player extends Entity{
	private Inventory inventory;
	private Equipment[] equipment;
	
	public Player() {
		health = 100;
		width = 64;
		height = 64;
		inventory = new Inventory();
		equipment = new Equipment[5];
		
		try {
			image = new Image(SlimeGame.basePath + "res//player.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Player(int placeX, int placeY) {
		health = 100;
		width = 64;
		height = 64;
		inventory = new Inventory();
		equipment = new Equipment[5];
		
		try {
			image = new Image(SlimeGame.basePath + "res//player.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		x = placeX;
		y = placeY;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);

	}



	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(faceRight) {
			g.drawImage(image, (int)x, (int)y, (int)x + 64,(int) y + 64,64,0,128,64);
		} else if(faceLeft) {
			g.drawImage(image, (int)x, (int)y, (int)x + 64,(int) y + 64,0,0,64,64);
		}
	}


	@Override
	public boolean onCollision(Entity entity) {
		return true;
	}
	
	@Override
	public void pickUp(Item item) {
		if(!inventory.add(item)){
			itemsPending.add(item);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setTurningRight(boolean b) {
		turningRight = b;
	}
	
	public void setTurningLeft(boolean b) {
		turningLeft = b;
	}
	
	public Rectangle getSpace() {
		return new Rectangle(x - MainGameState.TILE_SIZE, y - MainGameState.TILE_SIZE, width + MainGameState.TILE_SIZE, height + MainGameState.TILE_SIZE);
	}

	public int[] getInventoryIdArray() {
		return inventory.getIdArray();
	}

	public Equipment[] getEquipment() {
		return equipment;
	}
	
	public void setEquipment(Equipment[] equipment) {
		for(int i = 0; i < 4; i++) {
			if(equipment[i] != null) {
				this.equipment[i] = equipment[i];
			}
		}
	}

	public int[] getEquipmentIdArray() {
		int[] idArray = new int[equipment.length];
		for(int i = 0; i < equipment.length; i++) {
			idArray[i] = -1;
			if(equipment[i] != null) {
				idArray[i] = equipment[i].getId();
			}
		}
		return idArray;
	}
	
}

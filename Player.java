package com.cannon.basegame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Player extends Entity{
	private Inventory inventory;
	public Player() {
		health = 100;
		width = 64;
		height = 64;
		inventory = new Inventory();
		
		try {
			image = new Image(SlimeGame.basePath + "res\\player.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Player(int placeX, int placeY) {
		health = 100;
		width = 64;
		height = 64;
		inventory = new Inventory();
		
		try {
			image = new Image(SlimeGame.basePath + "res\\player.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
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
		inventory.add(item);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}

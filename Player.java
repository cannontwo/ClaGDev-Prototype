package com.cannon.basegame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;


public class Player extends Entity{
	private Inventory inventory;
	
	public Player() {
		health = 100;
		width = 64;
		height = 64;
		inventory = new Inventory();
		
		try {
			image = new Image(SlimeGame.basePath + "res//player.png");
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
			image = new Image(SlimeGame.basePath + "res//player.png");
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

	public static Player restorePlayer(File playerSaveFile) {
		Player tempPlayer = new Player();
		PlayerData tempPlayerData = null;
		
		Gson gson = new Gson();
		
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(playerSaveFile)));
			if(reader.hasNext()) {
				tempPlayerData = gson.fromJson(reader.next(),PlayerData.class);
				tempPlayer.x = tempPlayerData.getX();
				tempPlayer.y = tempPlayerData.getY();
				tempPlayer.inventory = new Inventory(tempPlayerData.getInventoryIdArray());
				tempPlayer.health = tempPlayerData.getHealth();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return tempPlayer;
	}
	
	public void savePlayer() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(SlimeGame.basePath + "//res//playersave.json"));
			Gson gson = new Gson();
			
			PlayerData playerData = new PlayerData(this);
			
			writer.write(gson.toJson(playerData));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getInventoryIdArray() {
		return inventory.getIdArray();
	}
	
	
}

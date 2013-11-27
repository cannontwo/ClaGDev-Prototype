package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Player extends Actor{
	private Inventory inventory;
	private MeleeAction actionType = new MeleeAction(this);
	
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
		if(itemsPending.size() > 0 && itemThrowDelay++ % 5 == 0){
			throwItem();
			
		}
		if(itemThrowDelay > 100){
			itemThrowDelay = 0;
		}
		
		actionType.act();		

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
		if(actionFlag){
			return actionType.onCollision(entity);
		}
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

//	public static Player restorePlayer(File playerSaveFile) {
//		Player tempPlayer = new Player();
//		EntityData tempPlayerData = null;
//		
//		Gson gson = new Gson();
//		
//		try {
//			Scanner reader = new Scanner(new BufferedReader(new FileReader(playerSaveFile)));
//			if(reader.hasNext()) {
//				tempPlayerData = gson.fromJson(reader.next(),EntityData.class);
//				tempPlayer.x = tempPlayerData.getX();
//				tempPlayer.y = tempPlayerData.getY();
//				tempPlayer.inventory = new Inventory(tempPlayerData.getInventoryIdArray());
//				tempPlayer.health = tempPlayerData.getHealth();
//			}
//			reader.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		return tempPlayer;
//	}
	
//	public void savePlayer() {
//		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(SlimeGame.basePath + "//res//playersave.json"));
//			Gson gson = new Gson();
//			
//			EntityData playerData = new EntityData(this);
//			
//			writer.write(gson.toJson(playerData));
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public int[] getInventoryIdArray() {
		return inventory.getIdArray();
	}

	@Override
	public HashMap<String, Integer> getStats() {
		// TODO Auto-generated method stub
		return stats;
	}

	@Override
	public int getStat(String stat) {
		return stats.get(stat);
	}

	@Override
	public void initStats() {
		HashMap<String, Integer> tempStats = new HashMap<String, Integer>();
		Gson myGson = new Gson();
		Type hashType = new TypeToken<HashMap<String, Integer>>() {}.getType();
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(SlimeGame.basePath + "data//playerStats.json")));
			while(reader.hasNext()){
				tempStats = myGson.fromJson(reader.next(), hashType);
				actionType.toggleActions(tempStats.get("MeleeAction"));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		stats = tempStats;
		maxSpeedX = stats.get("MaxSpeedX");
		maxSpeedY = stats.get("MaxSpeedY");
}

	public void doAction(){
		actionFlag = true;
	}
	
	
}

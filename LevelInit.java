package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LevelInit {

	private ArrayList<Item> itemList;
	private int playerX;
	private int playerY;
	
	public LevelInit(String ref) {
		Gson myGson = new Gson();
		Scanner reader;
		
		try {
			reader = new Scanner(new BufferedReader(new FileReader(ref)));
			if(reader.hasNext()) {
				LevelInit temp = myGson.fromJson(reader.next(), LevelInit.class);
				this.playerX = temp.playerX;
				this.playerY = temp.playerY;
				if(reader.hasNext()) {
					ArrayList<Item> tempItemList = myGson.fromJson(reader.next(), new TypeToken<ArrayList<Item>>(){}.getType());
					for(Item item : tempItemList) {
						item.setId(item.getId());
					}
					this.itemList = tempItemList;
				}
			} 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public LevelInit(int playerX, int playerY) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.itemList = new ArrayList<Item>();
	}
	
	public float getPlayerX() {
		return playerX;
	}
	public float getPlayerY() {
		return playerY;
	}
	public ArrayList<Item> getEntities() {
		return itemList;
	}

}

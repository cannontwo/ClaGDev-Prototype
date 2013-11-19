package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Area {

	private static Area areaControl;
	private TiledMap map;
	private boolean[][] blockedArray;
	private static String levelFile = SlimeGame.basePath+"res//level_tut.tmx";
	
	public Area() {
		try {
			map = new TiledMap(levelFile);
			File saveFile = new File(SlimeGame.basePath + "res//savefile.json");
			if(saveFile.exists()) {
				HashMap<List<Integer>,Integer> restoredArray = restoreMap(saveFile);
				MainGameState.setChangedTileList(restoredArray);
				ArrayList<List<Integer>> removeLaterList = new ArrayList<List<Integer>>();
				
				for(List<Integer> intArray : restoredArray.keySet()) {
					if(map.getTileId(intArray.get(0), intArray.get(1), 0) == restoredArray.get(intArray)) {
						removeLaterList.add(intArray);
					} else {
						map.setTileId(intArray.get(0), intArray.get(1), 0, restoredArray.get(intArray));
					}
				}
				
				for(List<Integer> toRemove : removeLaterList) {
					restoredArray.remove(toRemove);
				}
			}
			
			
			updateBlocked(0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render() throws SlickException {
		map.render(0,0);
	}
	
	public void updateBlocked(int mapNum) {
		blockedArray = new boolean[map.getWidth()][map.getHeight()];
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y ++) {
				int tileID = map.getTileId(x, y, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if(!"false".equals(value)) {
					blockedArray[x][y] = true;
				} else {
					blockedArray[x][y] = false;
				}
			}
		}
	}
	
	public void saveMap(HashMap<List<Integer>,Integer> changedTileList) {
		try {
			Type hashType = new TypeToken<HashMap<List<Integer>,Integer>>(){}.getType();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(SlimeGame.basePath + "//res//savefile.json"));
			GsonBuilder builder = new GsonBuilder();

	        Gson gson = builder.enableComplexMapKeySerialization().create();
			
			writer.write(gson.toJson(changedTileList, hashType));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private HashMap<List<Integer>, Integer> restoreMap(File saveFile) {
		Type hashType = new TypeToken<HashMap<List<Integer>,Integer>>() {}.getType();
		Gson myGson = new Gson();
		
		HashMap<List<Integer>,Integer> tempHash = new HashMap<List<Integer>,Integer>();
		
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(saveFile)));
			if(reader.hasNext()) {
				tempHash = myGson.fromJson(reader.next(),hashType);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return tempHash;
	}

	public static Area getAreaControl() {
		return areaControl;
	}
	
	public TiledMap getMap(int index) {
		return map;
	}

	public static void init() {
		areaControl = new Area();
		
	}

	public boolean getTileBlocked(int iX, int iY) {
		return blockedArray[iX][iY];
	}
	
	public String getLevelDataFile() {
		return levelFile;
	}
}

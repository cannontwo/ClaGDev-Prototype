package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Statistics {
	static HashMap<String,HashMap<String,Integer>> entityStatisticsList;
	private static HashMap<Integer,HashMap<String,Integer>> equipmentStatisticsList;

	public static void init() {
		Type entityType = new TypeToken<HashMap<String,HashMap<String,Integer>>>() {}.getType();
		Type equipmentType = new TypeToken<HashMap<Integer,HashMap<String,Integer>>>() {}.getType();
		
		Gson gson = new Gson();
		
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(new File(SlimeGame.basePath + "data//entitystatistics.json"))));
			if(reader.hasNext()) {
				entityStatisticsList = gson.fromJson(reader.next(), entityType);
			}
			reader.close();
			
			reader = new Scanner(new BufferedReader(new FileReader(new File(SlimeGame.basePath + "data//equipmentstatistics.json"))));
			if(reader.hasNext()) {
				equipmentStatisticsList = gson.fromJson(reader.next(), equipmentType);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static HashMap<String,Integer> getEquipmentStats(int id) {
		return equipmentStatisticsList.get(0);
	}
	
	public static HashMap<String,Integer> getEntityStats(String key) {
		try {
			return entityStatisticsList.get(key);
		} catch(NullPointerException e) {
			return new HashMap<String,Integer>();
		}
	}

}

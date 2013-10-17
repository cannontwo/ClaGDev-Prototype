package com.cannon.basegame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ThrowAway{
	
	public static void main(String[] args) {
			HashMap<Integer,String> map = new HashMap<Integer,String>();
			map.put(0,"fang");
			map.put(1, "stick");
			map.put(2, "rock");
			map.put(3, "feather");
			Type mapType = new TypeToken<HashMap<Integer,String>>() {}.getType();
			
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/cannon/basegame/data/items.json"));
				Gson myGson = new Gson();
				writer.write(myGson.toJson(map,mapType));
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

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
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MeleeEnemy extends Actor {
	
	public static HashMap<Integer, String> meleeEnemyTypeList;
	
	private MeleeAction actionType;
	private HashMap <String, Integer> stats = null;
	private int id;
	public MeleeEnemy(int id) {
		
		this.id = id;
		actionType = new MeleeAction(this);
		initStats();
		maxSpeedX = stats.get("MaxSpeedX");
		maxSpeedY = stats.get("MaxSpeedY");
		width = height = 32;
		try {
			setImage(SlimeGame.basePath + "res//" + meleeEnemyTypeList.get(id)+ ".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void initList(){
		meleeEnemyTypeList = new HashMap<Integer,String>();
		Type mapType = new TypeToken<HashMap<Integer,String>>() {}.getType();
		Gson myGson = new Gson();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(SlimeGame.basePath + "data//meleeEnemies.json"));
			meleeEnemyTypeList = myGson.fromJson(reader.readLine(), mapType);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(image, x, y);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		actionType.act();
		

		
	}

	@Override
	public boolean onCollision(Entity entity) {
		return actionType.onCollision(entity);
	}
	
	public void onDeath(){
		try {
			while(itemsPending.size() > 0){
				Item temp = itemsPending.get(0);
				itemsPending.set(0, new Item(temp.getId(), (int) x, (int) y));
				throwItem();
			}
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setImage(String image) throws SlickException{
		this.image = new Image(image);
	}
	
	public HashMap<String, Integer> getStatsFromId(int id){
		HashMap<String, Integer> tempStats = new HashMap<String, Integer>();
		Gson myGson = new Gson();
		Type hashType = new TypeToken<HashMap<String, Integer>>() {}.getType();
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(SlimeGame.basePath + "data//" + meleeEnemyTypeList.get(id) + ".json")));
			while(reader.hasNext()){
				tempStats = myGson.fromJson(reader.next(), hashType);
				actionType.toggleActions(tempStats.get("MeleeAction"));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(tempStats);
		return tempStats;
	}
		
	public int getStat(String stat){
		return stats.get(stat);
	}
	
	public int getId(){
		return id;
	}
	
	public HashMap<String, Integer> getStats(){
		return stats;
	}

	@Override
	public void initStats() {
		stats = getStatsFromId(id);
		
	}
		
	
	

}

package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class Enemy extends Actor {

	//must be initialized before any enemy
	public static HashMap<Integer, String> enemyTypeList; //<id, name>
	
	protected PassiveAction passiveActionType; //movement
	protected int id;
	protected String name;
	
	public Enemy(){
		this(69);
	}
	public Enemy(int id) {
		this.id = id;
		name = enemyTypeList.get(id);
		passiveActionType = new PassiveAction(this);
		try {
			image = new Image(SlimeGame.basePath + "res//" + name + ".png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionFlag = true;
		
	}

	@Override
	public void initStats() {
		stats = new HashMap<String, Integer>();
		stats.putAll(Actor.defaultStats());
		stats.putAll(Statistics.getEntityStats(name));
		Actor.setFields(this, stats);
	}
	
	
	@Override
	public void initActions() {
		String[] leftovers = passiveActionType.addActionTypes(Statistics.entityActionsList.get(name));
		leftovers = actionType.addActionTypes(leftovers);
		
		System.out.print("Used Methods: ");
		String[] usedMethods = Statistics.entityActionsList.get(name);
		for(String usedMethod: usedMethods){
			boolean used = true;
			for(String unusedMethod: leftovers){
				if(usedMethod.equals(unusedMethod) ){
					used = false;
					break;
				}	
			}
			if(used){
				System.out.print(usedMethod + ", ");
			}
		}
		System.out.println();
		System.out.println("Unused Methods: " + Arrays.asList(leftovers));

		
	}
	@Override
	public boolean onCollision(Entity entity){
		passiveActionType.onCollision(entity);
		return true;
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(canControlMovement){
			passiveActionType.act(delta);
		}
		
	}

	public static void initEnemyList(){
		Gson gson = new Gson();
		Type hashType = new TypeToken<HashMap<Integer, String>>() {}.getType();
		try{
			Scanner reader = new Scanner(new BufferedReader(new FileReader(new File(SlimeGame.basePath + "data//enemylist.json"))));
			if(reader.hasNext()){
				enemyTypeList = gson.fromJson(reader.next(), hashType);
			}
			reader.close();
		} catch(FileNotFoundException e) {
			
		}
	}
	
	@Override
	public int getId(){
		return id;
	}
	
}

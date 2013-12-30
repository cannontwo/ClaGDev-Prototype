package com.cannon.basegame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Actor extends Entity {

	public Action actionType;
	public boolean actionFlag;
	public boolean attacking = false;
	public HashMap<String, Integer> stats;
	public int strength = 0;
	
	public Actor() {
		actionFlag = false;
		//initStats();
		//initActions();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(actionFlag){
			actionType.act(delta);
		}
	}

	@Override
	public boolean onCollision(Entity entity) {
		// TODO Auto-generated method stub
		if(actionFlag){
			return actionType.onCollision(entity);
		}
		return true;
	}
	public static final HashMap<String, Integer> defaultStats(){
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		stats.put("width", 32);
		stats.put("height", 32);
		stats.put("health", 25);
		stats.put("strength", 10);
		stats.put("maxSpeedX", 20);
		stats.put("maxSpeedY", 20);
		stats.put("defense", 0);
		return stats;
	}
	
	public static void setFields(Actor actor, HashMap<String, Integer> statsMap){
		Field[] fields = actor.getClass().getFields();
		for(Map.Entry<String, Integer> entry : statsMap.entrySet()){
			for(Field field: fields){
				if(entry.getKey().equals(field.getName())){
					try {
						field.setInt(actor, entry.getValue());
						System.out.println("Set "+ actor + "'s " + field.getName() + " to " + entry.getValue());
						break;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	protected void hit(Entity entity){
		if(entity.safeTimer > 0){
			return;
		}
		int netDamage = strength - entity.defense;
		if(netDamage < 0){
			netDamage = 0;
		}
		entity.health -= netDamage;
		entity.speedX = (faceRight) ? entity.maxSpeedX : -entity.maxSpeedX;
		entity.safeTimer = 500;
		entity.speedY = -10;
	}
	
	public HashMap<String, Integer> getStats(){
		return stats;
	}
	
	public abstract void initStats();
	public abstract void initActions();

}

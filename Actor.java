package com.cannon.basegame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;

public abstract class Actor extends Entity {

	public Action actionType;
	public boolean actionFlag;
	public HashMap<String, Integer> stats;
	protected int strength = 0;
	
	public Actor() {
		actionFlag = false;
		//initStats();
		//initActions();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		//if(actionFlag){
			actionType.act();
		//}
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
		return stats;
	}
	
	public abstract void initStats();
	public abstract void initActions();

}

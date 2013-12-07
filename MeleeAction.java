package com.cannon.basegame;

import java.util.Calendar;
import java.util.HashMap;

public class MeleeAction extends Action {

	public static HashMap<Integer, String> meleeActionTypes;
	public int attackTimer = 0;
	public int cooldownTimer = 0;
	public Calendar timeOfAction;
	
	public MeleeAction(Actor actor){
		super(actor);
	}


	@Override
	public boolean onCollision(Entity entity) {

		return false;
	}
	
	
	//should be passive, but i needed something to test
	public void jump(){
		if(!timersAreDone()){
			return;
		}
		actor.jump();
		/*timeOfAction = Calendar.getInstance();
		attackTimer = 1;
		cooldownTimer = 3;*/
	}

	private boolean timersAreDone() {
		if(attackTimer > 0){
			if(Action.secondHasPassed(timeOfAction)){
				attackTimer--;
				timeOfAction = Calendar.getInstance();
			}
			return false;
		}
		if(cooldownTimer > 0){
			if(Action.secondHasPassed(timeOfAction)){
				cooldownTimer--;
				timeOfAction = Calendar.getInstance();
			}
			return false;
		}
		return true;
	}
	

	
	
	

}

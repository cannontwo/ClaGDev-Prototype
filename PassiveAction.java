package com.cannon.basegame;

public class PassiveAction extends Action {
	
	public PassiveAction(Actor actor) {
		super(actor);
	}
	


	public boolean onCollision(Entity entity) {
		return true;
	}
	
	public void stand_still(){
		
	}

}

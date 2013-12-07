package com.cannon.basegame;

public class PassiveAction extends Action {
	
	public PassiveAction(Actor actor) {
		super(actor);
	}
	


	public boolean onCollision(Entity entity) {
		return true;
	}
	private void activateActionFlag(int delta, int cooldown){
		if(actor.actionFlag || !timersAreDone(delta)){
			return;
		}
		System.out.println("Set ActionFlag");
		actor.actionFlag = true;
		cooldownTimer = cooldown;

	}
	public void stand_still(int delta){
		activateActionFlag(delta, 1000);
	}
	
	public void moveTowardsPlayer(int delta){
		Player player = Entity.getPlayer();
		if(player.x > actor.x){
			actor.moveRight = true;
			actor.moveLeft = false;
		} else{
			actor.moveLeft = true;
			actor.moveRight = false;
		}
		activateActionFlag(delta, 500);
	}

}

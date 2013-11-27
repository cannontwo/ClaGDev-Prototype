package com.cannon.basegame;

import java.util.Calendar;

public class MeleeAction implements Action {
	
	private Actor actor;
	private Entity target = null;
	private Calendar lastRegisteredTime = Calendar.getInstance();
	private int attackTimer = -1; //how long it takes to attack in seconds
	private boolean[] toggledActions;
	public MeleeAction(Actor actor) {
		this.actor = actor;
		toggledActions = new boolean[2];
		for(int x = 0; x < toggledActions.length; x++){
			toggledActions[x] = false;
		}
	}

	@Override
	public void act() {
		if(actor.attacking){
			if(Calendar.getInstance().get(Calendar.SECOND) > lastRegisteredTime.get(Calendar.SECOND) ||
					Calendar.getInstance().get(Calendar.MINUTE) > lastRegisteredTime.get(Calendar.MINUTE)){
				attackTimer--;
			} else if(attackTimer > 0){
				return;
			}
		}
		actor.attacking = false;
		
		if(toggledActions[MOVE_TOWARDS_PLAYER]){
			moveTowardsPlayer();
		}
		if(toggledActions[CHARGE]){
			charge();
			return;
		}

	}
	
	public boolean onCollision(Entity entity){
		if(actor.attacking){
			if(actor.hit(entity, actor.getStat("Strength"))){
			}
		}
		return true;
	}
	
	
	private void charge(){
		if(target != null){
			if(actor.getDistance(target.getX(), target.getY()) > 128){
				return;
			}
		}
		actor.maxSpeedX = actor.getStat("ChargeSpeed");
		actor.speedX = (actor.faceRight) ? actor.maxSpeedX : -actor.maxSpeedX;
		actor.attacking = true;
		lastRegisteredTime = Calendar.getInstance();
		attackTimer = 5;
	}
	
	private void moveTowardsPlayer(){
		Entity target = Entity.getPlayer();
		this.target = target;
		if(actor.getDistance(target.getX() + target.getWidth() / 2, target.getY() + target.getHeight() / 2) < 128){
			
		}
		if(target.getX() + target.getWidth() / 2 > actor.getX()){
			actor.moveRight = true;
			actor.moveLeft = false;
		} 
		if(target.getX() + target.getWidth() / 2 < actor.getX()){
			actor.moveLeft = true;
			actor.moveRight = false;
		}
		if(target.getX() == actor.getX()){
			actor.moveLeft = false;
			actor.moveRight = false;
		}

		if( (actor.speedX == 0 && (actor.moveLeft || actor.moveRight)) || Math.abs(target.getX() - actor.getX()) < 10){
			actor.jump();
		}
		
	}
	
	public void toggleActions(int actionId){
		toggledActions[actionId] = !toggledActions[actionId];
	}
	
	
	public static final int CHARGE = 0;
	public static final int MOVE_TOWARDS_PLAYER = 1;

}

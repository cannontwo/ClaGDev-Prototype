package com.cannon.basegame;

import java.util.HashMap;

public class MeleeAction implements Action {
	
	public static HashMap<Integer, String> meleeActionList = new HashMap<Integer, String>();
	private int id;
	private MeleeEnemy actor;
	
	public MeleeAction(MeleeEnemy actor, int id) {
		this.actor = actor;
		this.id = id;
	}

	@Override
	public void act() {
		if(meleeActionList.get(id).equals("Charge")){
			charge();
			return;
		}

	}
	
	public boolean onCollision(Entity entity){
		if(entity instanceof Player){
			if(actor.hit(entity, actor.getStat("Strength"))){
			}
		}
		return true;
	}
	
	public static void initMeleeActions(){
		meleeActionList.put(0, "Charge");

	}
	
	private void charge(){
		Player player = Entity.getPlayer();
		if(actor.getDistance(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2) < 128){
			actor.maxSpeedX = actor.getStat("ChargeSpeed");
		} else {
			actor.maxSpeedX = actor.getStat("MaxSpeedX");
		}
		if(player.getX() + player.getWidth() / 2 > actor.getX()){
			actor.moveRight = true;
			actor.moveLeft = false;
		} 
		if(player.getX() + player.getWidth() / 2 < actor.getX()){
			actor.moveLeft = true;
			actor.moveRight = false;
		}
		if(player.getX() == actor.getX()){
			actor.moveLeft = false;
			actor.moveRight = false;
		}

		if( (actor.speedX == 0 && (actor.moveLeft || actor.moveRight)) || Math.abs(player.getX() - actor.getX()) < 10){
			actor.jump();
		}
	}
	

}

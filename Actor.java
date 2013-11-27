package com.cannon.basegame;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Actor extends Entity{
	
	public abstract HashMap<String, Integer> getStats();
	public abstract int getStat(String stat);
	public abstract void initStats();
	
	protected boolean attacking = false;
	protected HashMap <String, Integer> stats = null;
	protected boolean actionFlag;
	
	public boolean hit(Entity entity, int damage){
		if(entity.safeTimer > 0){
			return false;
		}
		entity.safeTimer = 50;
		entity.health -= damage;
		if(faceRight){
			entity.speedX = entity.maxSpeedX;
		}
		else
			entity.speedX = -maxSpeedX;
		entity.speedY = -maxSpeedY;
		return true;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(health <= 0) {
			setDead(true);
		}
		float speedFactor = ((float)delta / 1000f) * 10f;
		
		
		if(hasGravity) {
			accelerationY = 6f;
		}
		
		if(jumpTime > 0) {
			maxSpeedY = 18;
			accelerationY -= jumpTime * (500 / 12);
			jumpTime -= delta;
		} else {
			maxSpeedY = 40;
		}
				

		
		speedY += accelerationY * speedFactor;
		if(speedY > maxSpeedY)
			speedY = maxSpeedY;
		if(speedY < -maxSpeedY) 
			speedY = -maxSpeedY;
		
		if(attacking){
			onMove(speedX, speedY, speedFactor);
			if(safeTimer > 0){
				safeTimer--;
			}
			return;
		}

		//Check if we're not moving anymore
		
		if(moveLeft == false && moveRight == false) {
			stopMove();
		}
		
		if(moveLeft) {
			faceRight = false;
			faceLeft = true;
			accelerationX = -6f;
			if(turningLeft && speedX >= 0) {
				accelerationX = -11;
			} else {
				turningLeft = false;
			}
		} else if(moveRight) {
			faceLeft = false;
			faceRight = true;
			accelerationX = 6f;
			if(turningRight && speedX <= 0) {
				accelerationX = 11;
			} else {
				turningRight = false;
			}
		}
				
		
		
		speedX += accelerationX * speedFactor;
		
				
		if(speedX > maxSpeedX)
			speedX = maxSpeedX;
		if(speedX < -maxSpeedX)
			speedX = -maxSpeedX;
		
		
		onMove(speedX, speedY, speedFactor);
		
		if(safeTimer > 0){
			safeTimer--;
		}
	}
	
	public String toString(){
		String returnString = getClass().toString();
		returnString = returnString.substring(returnString.lastIndexOf('.') + 1);
		return returnString;
	}

}

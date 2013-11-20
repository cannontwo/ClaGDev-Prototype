package com.cannon.basegame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;


public abstract class Entity {
	//Size
	protected int width = 0;
	protected int height = 0;
	//Position
	protected float x = 0;
	protected float y = 0;
	//Speed
	protected double speedX = 0;
	protected double speedY = 0;
	//Maximum Speed
	protected double maxSpeedX = 27;
	protected double maxSpeedY = 35;
	//jumpTime controls Jump speed and max time
	protected double jumpTime = 0;
	//Acceleration
	protected double accelerationX = 0;
	protected double accelerationY = 0;
	//Is the Entity moving?
	protected boolean moveLeft = false;
	protected boolean moveRight = false;
	//Which direction are we facing?
	protected boolean faceRight = true;
	protected boolean faceLeft = false;
	//Is the entity affected by gravity
	protected boolean hasGravity = true;
	//Is the entity affected by collisions
	protected boolean canCollide = true;
	//Can the entity jump?
	protected boolean canJump = false;
	//Is the entity dead?
	private boolean dead = false;
	//Entity health
	protected int health = 20;
	//Image to display
	protected Image image;
	//Faster turn than run
	protected boolean turningLeft = false;
	protected boolean turningRight = false;
	
	//List of items to be thrown
	public ArrayList<Item> itemsPending = new ArrayList<Item>();
	
	//Delay so all the items don't get thrown at once
	private short itemThrowDelay = 0;
	
	//Static list of all entities
	public static ArrayList<Entity> entityList = new ArrayList<Entity>();
	public static Player getPlayer(){
		for(Entity entity: entityList){
			if(entity instanceof Player)
				return (Player)entity;
		}
		return null;
	}
	
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;
	public abstract void init(GameContainer container, StateBasedGame game) throws SlickException;
	public abstract boolean onCollision(Entity entity);
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(health <= 0) {
			dead = true;
		}
		
		float speedFactor = ((float)delta / 1000f) * 10f;
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
				
		speedX += accelerationX * speedFactor;
		speedY += accelerationY * speedFactor;
				
		if(speedX > maxSpeedX)
			speedX = maxSpeedX;
		if(speedX < -maxSpeedX)
			speedX = -maxSpeedX;
		if(speedY > maxSpeedY)
			speedY = maxSpeedY;
		if(speedY < -maxSpeedY) 
			speedY = -maxSpeedY;
		
		onMove(speedX, speedY, speedFactor);
		
		if(itemsPending.size() > 0 && itemThrowDelay++ % 5 == 0){
			throwItem();
			
		}
		if(itemThrowDelay > 100){
			itemThrowDelay = 0;
		}
	}
	
	protected void stopMove() {
		if(speedX > 0) {
			accelerationX = -11f;
		}
		
		if(speedX < 0) {
			accelerationX = 11f;
		}
		
		if(speedX < 2.0f && speedX > -2.0f) {
			accelerationX  = 0;
			speedX = 0;
		}
	}
	
	protected void onMove(double moveX, double moveY, double speedFactor) {
		canJump = false;
		
		if(moveX == 0 && moveY == 0) {
			return;
		}
		
		moveX *= speedFactor;
		moveY *= speedFactor;
		
		double newX = 0, newY = 0;
		
		if(moveX != 0) {
			if(moveX >= 0) newX = speedFactor;
			else newX = -speedFactor;
		}
		
		if(moveY != 0) {
			if(moveY >= 0) newY = speedFactor;
			else newY = -speedFactor;
		}
		
		while(true) {
			if(!canCollide) {
				posValid((x + newX), (y + newY));
				
				x += newX;
				y += newY;
			} else {
				
				//If the new position is valid or not in a horizontal direction
				if(posValid((x + newX),y) && x + newX > 0 && x + newX + width < Area.getAreaControl().getMap(0).getWidth() * MainGameState.TILE_SIZE) {
					x += newX;
				} else {
					speedX = 0;
				}
				
				//if the new position is valid or not in a vertical direction
				if(posValid(x,(y + newY)) && y > 0) {
					y += newY;
				} else {
					if(moveY > 0) {
						canJump = true;
					}
					speedY = 0;
				}
			}
			
			moveX += -newX;
			moveY += -newY;
			
			if(newX > 0 && moveX <= 0) newX = 0;
	        if(newX < 0 && moveX >= 0) newX = 0;

	        if(newY > 0 && moveY <= 0) newY = 0;
	        if(newY < 0 && moveY >= 0) newY = 0;

	        if(moveX == 0) newX = 0;
	        if(moveY == 0) newY = 0;

	        if(moveX == 0 && moveY == 0)     break;
	        if(newX  == 0 && newY == 0)     break;
		}
	}

	public void onDeath(){
		
	}
		
	public void throwItem() throws SlickException{
		Item item = new Item(itemsPending.get(0).getId());
		if(!faceLeft){
			item.setX((int) this.getX() - this.getWidth() / 2 - 5);
			item.setSpeedX(-30);
		}
		else{
			item.setX((int) this.getX() + this.getWidth() + 5);
			item.setSpeedX(30);
		}
		item.setY((int) this.getY() - 5);
		item.setSpeedY(-15);
		if(item.canBeThrown()){ 
			Entity.entityList.add(item);
			itemsPending.remove(0);
		}
		
	}

	
	protected boolean posValid(double newX, double newY) {
		boolean returnBool = true;
		
		int startX = (int) (newX / MainGameState.TILE_SIZE); 
		int startY = (int) (newY / MainGameState.TILE_SIZE);
		
		int endX = (int) ((newX + width - 5) / MainGameState.TILE_SIZE);
		int endY = (int) ((newY + height) / MainGameState.TILE_SIZE);
		
		for(int iY = startY; iY <= endY; iY++) {
			for(int iX = startX; iX <= endX; iX++) {
				if(Area.getAreaControl().getTileBlocked(iX,iY)) {
					
					returnBool = false;
					
				} 
			}
		}
		
		if(canCollide) {
			for(Entity entity : entityList) {
				if(!PosValidEntity(entity, (int)newX, (int)newY) && !(entity instanceof Item)) {
					returnBool = false;
				} 
			}
		}
		
		return returnBool ;
	}
	
	protected boolean PosValidEntity(Entity entity, int newX, int newY) {
		
		if(this != entity && entity != null && !entity.isDead() && entity.canCollide && entity.collides(newX, newY, width, height)) {
			
			EntityCollision entityCol = new EntityCollision(this, entity);
			EntityCollision.entityCollisionList.push(entityCol);
			
			return false;
		}
		
		return true;
	}
	
	protected boolean collides(int oX, int oY, int oW, int oH) {
		int left1, left2;
	    int right1, right2;
	    int top1, top2;
	    int bottom1, bottom2;

	    int tX = (int)x;
	    int tY = (int)y;

	    left1 = tX;
	    left2 = oX;

	    right1 = left1 + width - 1;
	    right2 = oX + oW - 1;

	    top1 = tY;
	    top2 = oY;

	    bottom1 = top1 + height - 1;
	    bottom2 = oY + oH - 1;


	    if (bottom1 < top2) return false;
	    if (top1 > bottom2) return false;

	    if (right1 < left2) return false;
	    if (left1 > right2) return false;

	    return true;
	}
	
	public boolean jump() {
		if(!canJump) {
			return false;
		} else if(jumpTime <= 0) {
			jumpTime = 300;
		}
		
		
		return true;
	}
	
	public void stopJump() {
		jumpTime = 0;
	}
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	public double getAccelerationX() {
		return accelerationX;
	}
	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}
	public double getAccelerationY() {
		return accelerationY;
	}
	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}
	public boolean getHasGravity() {
		return hasGravity;
	}
	public void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}
	public boolean getCanCollide() {
		return canCollide;
	}
	public void setCanCollide(boolean canCollide) {
		this.canCollide = canCollide;
	}
	public double getMaxSpeedX() {
		return maxSpeedX;
	}
	public void setMaxSpeedX(double maxSpeedX) {
		this.maxSpeedX = maxSpeedX;
	}
	public double getMaxSpeedY() {
		return maxSpeedY;
	}
	public void setMaxSpeedY(double maxSpeedY) {
		this.maxSpeedY = maxSpeedY;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public void pickUp(Item item) {
	}
	public void setId(int id){
	}
	public int getId(){
		return -1;
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	
	public static void saveEntities() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(SlimeGame.basePath + "//res//entitysave.json"));
			Gson gson = new Gson();
			
			writer.write("");
			
			for(Entity entity : entityList) {
				EntityData entityData = new EntityData(entity);		
				writer.append(gson.toJson(entityData) + "\n");
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Entity> restoreEntities(File entitySaveFile) {
		ArrayList<Entity> returnEntities = new ArrayList<Entity>();
		EntityData tempEntityData = null;
		
		Gson gson = new Gson();
		
		try{
			Scanner reader = new Scanner(new BufferedReader(new FileReader(entitySaveFile)));
			while(reader.hasNext()) {
				tempEntityData = gson.fromJson(reader.next(), EntityData.class);
				Entity addingEntity;
				switch(tempEntityData.getEntityType()) {
				case EntityData.PLAYER:
					Player tempPlayer = new Player();
					tempPlayer.setInventory(new Inventory(tempEntityData.getInventoryIdArray()));
					addingEntity = tempPlayer;
					break;
				case EntityData.ITEM:
					Item tempItem = new Item();
					tempItem.setId(tempEntityData.getItemId());
					addingEntity = tempItem;
					break;
				default:
					addingEntity = new Item();
					break;
				}
				
				addingEntity.x = tempEntityData.getX();
				addingEntity.y = tempEntityData.getY();
				addingEntity.health = tempEntityData.getHealth();
				
				returnEntities.add(addingEntity);
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnEntities;
	}

}

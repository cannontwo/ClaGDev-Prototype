package com.cannon.basegame;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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
	protected double maxSpeedX = 35;
	protected double maxSpeedY = 30;
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
	
	//Static list of all entities
	public static ArrayList<Entity> entityList = new ArrayList<Entity>();
	public static Entity getPlayer(){
		for(Entity entity: entityList){
			if(entity instanceof Player)
				return entity;
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
			accelerationX = -9f;
		} else if(moveRight) {
			faceLeft = false;
			faceRight = true;
			accelerationX = 9f;
		}
				
		if(hasGravity) {
			accelerationY = 6f;
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
	}
	
	protected void stopMove() {
		if(speedX > 0) {
			accelerationX = -8f;
		}
		
		if(speedX < 0) {
			accelerationX = 8f;
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
	
	public void spawnProjectile() {
		final Entity me=this;
		Projectile p=null;
		try {
			p=new Projectile(){
				@Override
				public void setFields() {
						setOwner(me);	
						setDamage(20);
						setSpeedX(50);
				}
			};
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(p!=null) Entity.entityList.add(p);
	}
	public void throwItem(Entity owner, Item projectile){
		final Entity own=owner;
		final Item item;
		item=new Item(projectile.getId());
		Projectile p=null;
		try {
			p=new Projectile(){
				@Override
				public void setFields() {
						setOwner(own);	
						faceLeft = !faceLeft;
						faceRight = !faceRight; //throw it behind you
						setDamage(0);
						setSpeedX(15);
						setSpeedY(-15);
						try {
							setImage(SlimeGame.basePath + "res//" + Item.itemList.get(item.getId()) + ".png");
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
				}
				
				public void onDeath(){
					item.setX((int)getLastValidX());
					item.setY((int)getLastValidY());
					entityList.add(item);
				}
				
				
			};
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(p!=null) Projectile.pendingProjectiles.add(p);

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
			for(int i = 0; i < entityList.size(); i++) {
				if(!PosValidEntity(entityList.get(i), (int)newX, (int)newY)) {
					returnBool = false;
				}
			}
		}
		
		return returnBool ;
	}
	
	private boolean PosValidEntity(Entity entity, int newX, int newY) {
		
		if(this != entity && entity != null && !entity.isDead() && entity.canCollide && entity.collides(newX, newY, width, height)) {
			
			EntityCollision entityCol = new EntityCollision(this, entity);
			EntityCollision.entityCollisionList.push(entityCol);
			
			return false;
		}
		
		return true;
	}
	
	private boolean collides(int oX, int oY, int oW, int oH) {
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
		}
		
		speedY = -maxSpeedY;
		
		return true;
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
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
}

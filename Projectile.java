package com.cannon.basegame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.util.ArrayList;


public abstract class Projectile extends Entity {

	private Entity owner;
	private int damage;
	private Image im;
	private double lastX;
	private double lastY;
	
	public static ArrayList<Projectile> pendingProjectiles = new ArrayList<Projectile>();
	
	public Projectile() throws SlickException {
		damage=0;
		im=new Image(SlimeGame.basePath + "res//rock.png");
		width=32;
		height=32;
		setHasGravity(true);
		lastX=-1;
		lastY=-1;
		setFields();
	}
	
	/**Specifies what the projectile does<br>
	 * To-Do List: setOwner(Entity), setDamage(int), setHasGravity(boolean), setSpeedX(int), setImage(String)<br>
	 * Optional:  setSize(int), setSize(int, int)**/
	public abstract void setFields();
	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(im, x, y);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException{
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		try{
			super.update(container, game, delta);
		} catch(ArrayIndexOutOfBoundsException e){
			setDead(true);
			return;
		}
		if(speedX != Math.abs(maxSpeedX)){
			if(faceRight) {
				speedX = maxSpeedX;
				accelerationX = 5;
			} 
			if(faceLeft) {
				speedX = -maxSpeedX;
				accelerationX = -5;
			}
		}
		setDead(!canSurvive());
	}
	
	public boolean canSurvive(){
		try{
			if(!posValid(x, y))  //if it his a wall
				return false;
		} catch(ArrayIndexOutOfBoundsException e){  //if it's off map
			return false;
		}
		if(getHasGravity()&&speedY==0) //if it landed
			return false;
		
		if(!isDead()){
			lastX = getX();
			lastY = getY();
		}
		return true;
		
	}
		
	public double getLastValidX(){
		if(lastX == -1)
			return getX();
		return lastX;
	}
	public double getLastValidY(){
		if(lastY == -1)
			return getY();
		return lastY;
	}
	

	@Override
	public boolean onCollision(Entity entity) {
		if(entity.equals(owner)){
			return false;
		}
		Entity.entityList.remove(this);
		entity.setHealth(entity.getHealth()-damage);
		return true;
	}
	
	public void setOwner(Entity e){
		owner=e;
		this.faceLeft=owner.faceLeft;
		this.faceRight=owner.faceRight;
		if(faceLeft){
			setX((int) owner.getX() - owner.getWidth() / 2 - 5);
		}
		if(faceRight)
			setX((int) owner.getX() + owner.getWidth() + 5); //adds 5 for a little leewayff
		setY((int) owner.getY());
		
	}
	public void setDamage(int x){
		damage = x;
	}
	public void setSpeedX(int x){
		maxSpeedX = x;
	}
	public void setImage(String s) throws SlickException{
		im = new Image(s);
	}
	public void setSize(int x){
		width = x;
		height = x;
	}
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}

}

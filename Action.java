package com.cannon.basegame;

public interface Action {
	public void act();
	public boolean onCollision(Entity entity);
}

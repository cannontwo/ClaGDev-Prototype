package com.cannon.basegame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MeleeEnemy extends Enemy {

	public MeleeEnemy(int id) {
		super(id);
		actionType = new MeleeAction(this);
		initStats();
		initActions();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(image, x, y);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onCollision(Entity entity){
		if(actionFlag){
			actionType.onCollision(entity);
		}
		return super.onCollision(entity);
	}
	
	@Override
	public String toString(){
		return name;
	}

}

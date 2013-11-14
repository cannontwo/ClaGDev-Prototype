package com.cannon.basegame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cannon.basegame.not_mine.BasicTWLGameState;
import com.cannon.basegame.not_mine.RootPane;

public class InventoryState extends BasicTWLGameState{
	

	private boolean changeState = false;
	private InventoryPanel inventoryPanel;
	private Inventory inventory;
	private boolean firstTime = true;
	private boolean exitFlag = false;


	@Override
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		
		inventoryPanel = new InventoryPanel(inventory, this);
		
		rp.add(inventoryPanel);
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		inventoryPanel.setPosition(SlimeGame.WIDTH - (SlimeGame.WIDTH / 2), 5);
		inventoryPanel.adjustSize();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		GameState mainGameState = game.getState(SlimeGame.MAINGAMESTATE);
		mainGameState.render(container, game, g);
		Rectangle rect = new Rectangle(((MainGameState)mainGameState).getCamera().getX(),((MainGameState)mainGameState).getCamera().getY(),SlimeGame.WIDTH,SlimeGame.HEIGHT);
		g.setColor(new Color(0.2f,0.2f,0.2f,0.6f));
		g.fill(rect);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(exitFlag) {
			container.exit();
		}
		if(changeState) {
			game.enterState(SlimeGame.MAINGAMESTATE);
			changeState = false;
		}
	}

	@Override
	public int getID() {
		return SlimeGame.INVENTORYSTATE;
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		
		switch(key) {
		case Input.KEY_ESCAPE:
			exitFlag  = true;
			break;
		}
	};

	@Override
	public void keyReleased(int key, char c) {
		super.keyPressed(key, c);
		
		switch(key) {
		case Input.KEY_TAB:
			changeState();
			break;
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		if(Permanents.getInventory() != null) {
			this.inventory = Permanents.getInventory();
		}
		if(!firstTime) {
			inventoryPanel.updateInventory();
		}
		firstTime = false;
		super.enter(container, game);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		inventoryPanel.leave();
		super.leave(container, game);
	}
	
	public void changeState(){
		inventoryPanel.clearRecipeSlots();
		changeState=!changeState;
	}

}

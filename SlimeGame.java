package com.cannon.basegame;
import java.net.URL;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.cannon.basegame.not_mine.TWLStateBasedGame;


public class SlimeGame extends TWLStateBasedGame{
	public static final String basePath = "src//com//cannon//basegame//";
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	
	public static final int MAINGAMESTATE = 0;
	public static final int INVENTORYSTATE = 1;
	public static final int PAUSESTATE = 2;
	
	public SlimeGame() {
		super("Slime Game");
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SlimeGame());
		app.setMinimumLogicUpdateInterval(20);
		app.setMaximumLogicUpdateInterval(50);
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.setShowFPS(false);
		app.start();    

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainGameState()); //Adds main game state
		addState(new InventoryState());
		addState(new PauseState());
		
	}

	@Override
	protected URL getThemeURL() {
		return SlimeGame.class.getResource("gui//theme.xml");
	}

}

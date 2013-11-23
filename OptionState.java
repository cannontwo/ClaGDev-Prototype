package com.cannon.basegame;

import java.util.HashMap;

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

public class OptionState extends BasicTWLGameState{

	private OptionPanel optionPanel;
	public HashMap<String, Boolean> options;
	
	public OptionState() {
		options = new HashMap<String, Boolean>();
		options.put("Back", false);
		options.put("FullScreen", false);
		options.put("ShowFPS", false);
	}
	
	protected RootPane createRootPane(){
		RootPane rp = super.createRootPane();
		optionPanel = new OptionPanel(this);
		rp.add(optionPanel);
		
		return rp;
	}
	
	protected void layoutRootPane(){
		optionPanel.adjustSize();
		optionPanel.setPosition(SlimeGame.WIDTH - SlimeGame.WIDTH / 2 - optionPanel.getWidth() / 2,
				SlimeGame.HEIGHT / 2 - optionPanel.getHeight() / 2);

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		
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
		if(options.get("Back")){
			game.enterState(SlimeGame.PAUSESTATE);
			options.put("Back", false);
			return;
		}
		container.setFullscreen(options.get("FullScreen"));
		container.setShowFPS(options.get("ShowFPS"));
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

	public void keyPressed(int key, char c){
		super.keyPressed(key, c);
		switch(key){
		case Input.KEY_ESCAPE:
			options.put("Back", true);
			break;
		}
	}
}

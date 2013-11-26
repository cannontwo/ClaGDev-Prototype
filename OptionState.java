package com.cannon.basegame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OptionState extends BasicTWLGameState{

	private OptionPanel optionPanel;
	public static HashMap<String, Boolean> options;
	
	public OptionState() {
		options = new HashMap<String, Boolean>();
		options.put("Back", false);
		options.put("FullScreen", false);
		options.put("ShowFPS", false);
		options.put("DeveloperMode", false);
	}
	
	protected RootPane createRootPane(){
		RootPane rp = super.createRootPane();
		optionPanel = new OptionPanel();
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
			options.put("Back", false);
			saveOptions();
			game.enterState(SlimeGame.PAUSESTATE);
			return;
		}
		container.setFullscreen(options.get("FullScreen"));
		container.getGraphics().setBackground(new Color(0, 100, 255));
		container.setShowFPS(options.get("ShowFPS"));
		((MainGameState) game.getState(SlimeGame.MAINGAMESTATE)).setDeveloperMode(options.get("DeveloperMode"));
		
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
	
	public void saveOptions(){
		try {
			Type hashType = new TypeToken<HashMap<String, Boolean>>(){}.getType();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(SlimeGame.basePath + "//res//saveoptions.json"));
			GsonBuilder builder = new GsonBuilder();

	        Gson gson = builder.enableComplexMapKeySerialization().create();
			
			writer.write(gson.toJson(options, hashType));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Boolean> restoreOptions(File optionSaveFile){
		Type hashType = new TypeToken<HashMap<String, Boolean>>() {}.getType();
		Gson myGson = new Gson();
		
		HashMap<String, Boolean> tempHash = new HashMap<String, Boolean>();
		
		try {
			Scanner reader = new Scanner(new BufferedReader(new FileReader(optionSaveFile)));
			if(reader.hasNext()) {
				tempHash = myGson.fromJson(reader.next(),hashType);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tempHash;
	}
}

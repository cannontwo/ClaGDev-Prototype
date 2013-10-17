package com.cannon.basegame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Cannon Lewis on Apr 22, 2013
 */

/**
 * @author s619532
 *
 */
public class HelloWorld extends BasicGame {
	
	public HelloWorld() {
		super("Hello World");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Hello World", 100,100);
		
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new HelloWorld());
		
		app.setDisplayMode(800, 600, false);
		app.start();
		
	}
}

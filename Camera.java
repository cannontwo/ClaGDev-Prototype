package com.cannon.basegame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {
	
	private int x, y;
	private int mapWidth, mapHeight;
	private Rectangle viewPort;
	public Camera(TiledMap map, int mapWidth, int mapHeight) {
		x = 0;
		y = 0;
		viewPort = new Rectangle(0,0,SlimeGame.WIDTH, SlimeGame.HEIGHT);
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public void translate(Graphics g, Entity player) {
		if(player.getX() - SlimeGame.WIDTH / 2 + (player.width / 2) < 0) {
			x = 0;
		} else if(player.getX() + SlimeGame.WIDTH / 2 + (player.width / 2) > mapWidth) {
			x = -mapWidth + SlimeGame.WIDTH;
		} else {
			x = (int) -player.getX() + SlimeGame.WIDTH / 2 - (player.width / 2);
		}
		
		if(player.getY() - SlimeGame.HEIGHT / 2 + (player.height / 2) < 0) {
			y = 0;
		} else if(player.getY() + SlimeGame.HEIGHT / 2 + (player.height / 2) > mapHeight) {
			y = -mapHeight + SlimeGame.HEIGHT;
		} else {
			y = (int) -player.getY() + SlimeGame.HEIGHT / 2 - (player.height / 2);
		}
		
		g.translate(x,y);
		viewPort.setX(-x);
		viewPort.setY(-y);
	}

	public float getY() {
		// TODO Auto-generated method stub
		return viewPort.getY();
	}

	public float getX() {
		// TODO Auto-generated method stub
		return viewPort.getX();
	}

}

package com.cannon.basegame;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Area {

	private static Area areaControl;
	private TiledMap map;
	private boolean[][] blockedArray;
	
	public Area() {
		try {
			map = new TiledMap(SlimeGame.basePath + "res\\level_tut.tmx");
			System.out.println(SlimeGame.class.getResource("res\\level_tut.tmx"));
			updateBlocked(0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render() throws SlickException {
		map.render(0,0);
	}
	
	public void updateBlocked(int mapNum) {
		blockedArray = new boolean[map.getWidth()][map.getHeight()];
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y ++) {
				int tileID = map.getTileId(x, y, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if(!"false".equals(value)) {
					blockedArray[x][y] = true;
				} else {
					blockedArray[x][y] = false;
				}
			}
		}
	}

	public static Area getAreaControl() {
		return areaControl;
	}
	
	public TiledMap getMap(int index) {
		return map;
	}

	public static void init() {
		areaControl = new Area();
		
	}

	public boolean getTileBlocked(int iX, int iY) {
		return blockedArray[iX][iY];
	}
}

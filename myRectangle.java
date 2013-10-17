package com.cannon.basegame;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Graphics;

public class myRectangle {

	private int startX;
	private int startY;
	private int lastX;
	private int lastY;

	public myRectangle(Point start, Point last) {
		this.startX = start.x;
		this.startY = start.y;
		this.lastX = last.x;
		this.lastY = last.y;
	}

	public void draw(Graphics g) {
		g.drawRect(Math.min(startX,lastX), Math.min(startY, lastY), Math.abs(startX - lastX), Math.abs(startY - lastY));
		
	}

	public void update(Point start, Point last) {
		this.startX = start.x;
		this.startY = start.y;
		this.lastX = last.x;
		this.lastY = last.y;
	}
	
	public boolean contains(int x, int y, int width, int height) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(new Rectangle(Math.min(startX,lastX), Math.min(startY, lastY), Math.abs(startX - lastX), Math.abs(startY - lastY)).contains(x+i,y+j)) {
					return true;
				}
			}
		}
		return false;
	}
}

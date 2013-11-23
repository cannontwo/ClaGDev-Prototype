package com.cannon.basegame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;

public class OptionPanel extends Widget{

	private Button fullScreenButton;
	private Button backButton;
	private Button fpsButton;
	
	public OptionPanel() {
		
		fullScreenButton = new Button();
		String temp = OptionState.options.get("FullScreen") ? "On":"Off";
		fullScreenButton.setText("Full Screen Mode: " + temp);
		fullScreenButton.addCallback(new Runnable() {

			@Override
			public void run() {
				OptionState.options.put("FullScreen", !OptionState.options.get("FullScreen"));
				String temp = OptionState.options.get("FullScreen") ? "On":"Off";
				fullScreenButton.setText("Full Screen Mode: " + temp);
			}
			
		});
		
		backButton = new Button();
		backButton.setText("Back");
		backButton.addCallback(new Runnable() {

			@Override
			public void run() {
				OptionState.options.put("Back", true);
				
			}
			
		});
		
		fpsButton = new Button();
		temp = OptionState.options.get("ShowFPS") ? "On":"Off";
		fpsButton.setText("Show FPS: " + temp);
		fpsButton.addCallback(new Runnable() {

			@Override
			public void run() {
				OptionState.options.put("ShowFPS", !OptionState.options.get("ShowFPS"));
				String temp = OptionState.options.get("ShowFPS") ? "On":"Off";
				fpsButton.setText("Show FPS: " + temp);
				
			}
			
		});
		add(fullScreenButton);
		add(backButton);
		add(fpsButton);
	}
	
	protected void layout(){
		fullScreenButton.adjustSize();
		fullScreenButton.setPosition(getX() + getWidth() / 4 - fullScreenButton.getWidth() / 2,
				getY() + getHeight() * 2/10 - fullScreenButton.getHeight() / 2);
		backButton.adjustSize();
		backButton.setPosition(getX() + backButton.getWidth() / 2,
				getY() + getHeight()  - backButton.getHeight());
		fpsButton.adjustSize();
		fpsButton.setPosition(getX() + getWidth() / 4 - fpsButton.getWidth() / 2,
				getY() + getHeight() * 4/10 - fpsButton.getHeight() / 2);
	}


}

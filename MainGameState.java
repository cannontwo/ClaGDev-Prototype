package com.cannon.basegame;


import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.cannon.basegame.not_mine.BasicTWLGameState;
import com.cannon.basegame.not_mine.RootPane;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;


public class MainGameState extends BasicTWLGameState{

	public static final int TILE_SIZE = 32;

	private Player player;
	private int mapWidth;
	private int mapHeight;
	private Camera camera;
	private int grabbedTileId = 99;
	private Button posButton;
	private Button inventoryButton;
	private Label selectionLabel;
	private Label displayLabel;
	private Image selectionImage;
	private boolean changeState = false;
	private boolean firstTime = true;
	
	public static RecipeBook recipeBook;
	
	
	
	@Override
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		
		displayLabel = new Label();
		displayLabel.setText("");
		
		posButton = new Button();
		posButton.setText("CurrentPos");
		posButton.addCallback(new Runnable() {
			public void run() {
				displayLabel.setText("Player X: " + (player.x / 32) + ", Player Y: " + (player.y / 32));
				posButton.giveupKeyboardFocus();
			}
		});
		
		inventoryButton = new Button();
		inventoryButton.setText("Inventory");
		inventoryButton.addCallback(new Runnable() {
			public void run() {
				changeState = true;
				inventoryButton.giveupKeyboardFocus();
			}
		});
		
		selectionLabel = new Label();
		selectionLabel.setText("");
		
		
		rp.add(posButton);
		rp.add(inventoryButton);
		rp.add(selectionLabel);
		rp.add(displayLabel);
		
		return rp;
		
	}

	@Override
	protected void layoutRootPane() {
		posButton.adjustSize();
		posButton.setPosition(SlimeGame.WIDTH - posButton.getWidth(), 0);
		inventoryButton.adjustSize();
		inventoryButton.setPosition(SlimeGame.WIDTH - inventoryButton.getWidth(), 50);
		selectionLabel.setMinSize(50,50);
		selectionLabel.setMaxSize(50,50);
		selectionLabel.adjustSize();
		selectionLabel.setPosition(SlimeGame.WIDTH - selectionLabel.getWidth() - 15, 100);
		displayLabel.adjustSize();
		displayLabel.setPosition((SlimeGame.WIDTH / 2) - (displayLabel.getWidth() / 2), SlimeGame.HEIGHT - 50);
		
	}

	@Override 
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		container.getGraphics().setBackground(new Color(0,100,255));
		Area.init();
		Item.initList();
		
		TiledMap map = Area.getAreaControl().getMap(0);
		
		LevelInit levelData = new LevelInit(Area.getAreaControl().getLevelDataFile().substring(0,Area.getAreaControl().getLevelDataFile().length()-3) + "json");
		
		player = new Player((int) levelData.getPlayerX(), (int) levelData.getPlayerY());
		Entity.entityList.add(player);
		
		for(Entity entity : levelData.getEntities()){
			Entity.entityList.add(entity);
		}
		
		mapWidth = map.getWidth() * map.getTileWidth();
		mapHeight = map.getHeight() * map.getTileHeight();
		camera = new Camera(map, mapWidth, mapHeight);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		if(!firstTime) {
			
			camera.translate(g, player);
			Area.getAreaControl().render();
			
			for(Entity entity : Entity.entityList) {
				entity.render(container, game, g);
			}
			
			int heartsToShow = Math.round(player.health / 20);
			
			for(int i = 0; i < heartsToShow; i++) {
				g.drawImage(new Image(SlimeGame.basePath + "res//heart.png"), camera.getX() + 30 + (i * 32), camera.getY() + 30);
			}
			
			if(grabbedTileId != 99 && grabbedTileId != 0) {
				selectionImage.draw(camera.getX() + SlimeGame.WIDTH - 56,camera.getY() + 109);
			}
		} else {
			firstTime = false;
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(int num = 0; num < Entity.entityList.size(); num++) {
			Entity.entityList.get(num).update(container, game, delta);
		}
		
		for(EntityCollision entityCollision : EntityCollision.entityCollisionList) {
			Entity entityA = entityCollision.a;
			Entity entityB = entityCollision.b;
			if(entityA == null || entityB == null) {
				continue;
			}
			
			if(entityA.isDead() || entityB.isDead()) {
				continue;
			}
			
			if(entityA.onCollision(entityB)) {
				entityB.onCollision(entityA);
			}
		}
		if(changeState) {
			game.enterState(SlimeGame.INVENTORYSTATE);
			changeState = false;
		}
		

		for(int x = 0; x < Entity.entityList.size(); x++){
			if(Entity.entityList.get(x).isDead()){
				if(!(Entity.entityList.get(x) instanceof Player)){
					Entity.entityList.get(x).onDeath();
					Entity.entityList.remove(x--);
				}
				else; //what do i do when the player dies?
			}
		}
	}

	@Override
	public int getID() {
		return SlimeGame.MAINGAMESTATE;
	}

	/************************************************
	 * EVENT LISTENERS
	 ************************************************/
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		switch(key) {
		case Input.KEY_A:
			player.moveLeft = true;
			player.moveRight = false;
			break;
		case Input.KEY_D:
			player.moveRight = true;
			player.moveLeft = false;
			break;
		case Input.KEY_W:
			player.jump();
			break;
		case Input.KEY_LEFT:
			player.moveLeft = true;
			player.moveRight = false;
			break;
		case Input.KEY_RIGHT:
			player.moveRight = true;
			player.moveLeft = false;
			break;
		case Input.KEY_SPACE:
			player.jump();
			break;
		case Input.KEY_F:
			Random rand = new Random();
			Item newItem = new Item(rand.nextInt(4));
			newItem.setX(rand.nextInt(Area.getAreaControl().getMap(0).getWidth() * MainGameState.TILE_SIZE));
			newItem.setY(10);
			Entity.entityList.add(newItem);
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		super.keyReleased(key, c);
		switch(key) {
		case Input.KEY_A:
			player.moveLeft = false;
			break;
		case Input.KEY_D:
			player.moveRight = false;
			break;
		case Input.KEY_LEFT:
			player.moveLeft = false;
			break;
		case Input.KEY_RIGHT:
			player.moveRight = false;
			break;
		case Input.KEY_TAB:
			changeState = true;
			break;
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(grabbedTileId != 99) {
			Area.getAreaControl().getMap(0).setTileId((int)((newx + camera.getX()) / TILE_SIZE), (int)((newy + camera.getY()) / TILE_SIZE), 0, grabbedTileId);
			Area.getAreaControl().updateBlocked(0);
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		super.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// TODO Auto-generated method stub
		super.mousePressed(button, x, y);
		if(button == Input.MOUSE_RIGHT_BUTTON) {
			grabbedTileId = Area.getAreaControl().getMap(0).getTileId((int)((x + camera.getX()) / TILE_SIZE), (int)((y + camera.getY()) / TILE_SIZE), 0);
			try {
				if(grabbedTileId != 0) {
					selectionImage = new Image(SlimeGame.basePath + "res//tile" + grabbedTileId + ".png");
				}
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(button == Input.MOUSE_LEFT_BUTTON) {
			if(grabbedTileId != 99) {
				Area.getAreaControl().getMap(0).setTileId((int)((x + camera.getX()) / TILE_SIZE), (int)((y + camera.getY()) / TILE_SIZE), 0, grabbedTileId);
				Area.getAreaControl().updateBlocked(0);
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		super.mouseReleased(button, x, y);
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(newValue);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		Permanents.setInventory(player.getInventory());
		super.leave(container, game);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		if(Permanents.getInventory() != null) {
			player.setInventory(Permanents.getInventory());
		}
		super.enter(container, game);
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	
	
	

}

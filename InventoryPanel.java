package com.cannon.basegame;

import java.awt.Rectangle;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.Widget;

public class InventoryPanel extends Widget {

	private Inventory inventory;
	
	private InventoryState invState;
	
	private int numSlotsX = 4;
	private int numSlotsY = 4;
	private final ItemSlot[] slots;
	
	private int slotSpacing;
	
	private ItemSlot dragSlot;
	private ItemSlot dropSlot;
	
	private ItemSlot recipeSlot1, recipeSlot2, recipeSlot3, recipeResultSlot;
	
	private Button craftButton;
	private Button closeButton;
	
	private RecipeBook recipeBook;

	public InventoryPanel(Inventory inventory, InventoryState state) {
		recipeBook = new RecipeBook();
		this.inventory = inventory;
		this.invState=state;
		this.slots = new ItemSlot[numSlotsX * numSlotsY];
		
		ItemSlot.DragListener listener = new ItemSlot.DragListener() {

			@Override
			public void dragStarted(ItemSlot slot, Event evt) {
				InventoryPanel.this.dragStarted(slot, evt);
				
			}

			@Override
			public void dragging(ItemSlot slot, Event evt) {
				InventoryPanel.this.dragging(slot,evt);
				
			}

			@Override
			public void dragStopped(ItemSlot slot, Event evt) {
				InventoryPanel.this.dragStopped(slot,evt);
				
			}
			
		};
		
		for(int i = 0; i < slots.length; i ++) {
			slots[i] = new ItemSlot();
			slots[i].setListener(listener);
			add(slots[i]);
			try {
				slots[i].setItem(this.inventory.get(i));
			} catch(NullPointerException e) {
				slots[i].setItem(new Item());
			}
		}
		
		recipeSlot1 = new ItemSlot();
		recipeSlot1.setListener(listener);
		add(recipeSlot1);
		recipeSlot1.setItem(null);
		
		recipeSlot2 = new ItemSlot();
		recipeSlot2.setListener(listener);
		add(recipeSlot2);
		recipeSlot2.setItem(null);
		
		recipeSlot3 = new ItemSlot();
		recipeSlot3.setListener(listener);
		add(recipeSlot3);
		recipeSlot3.setItem(null);
		
		recipeResultSlot = new ItemSlot();
		recipeResultSlot.setListener(listener);
		add(recipeResultSlot);
		recipeResultSlot.setItem(null);
		
		craftButton = new Button();
		craftButton.setText("Craft");
		craftButton.addCallback(new Runnable() {

			@Override
			public void run() {
				try {
					Item craftedItem = recipeBook.getItemFromIngredients(recipeSlot1.getItem().getId(), recipeSlot2.getItem().getId(), recipeSlot3.getItem().getId());
					if(craftedItem != null) {
						recipeResultSlot.setItem(craftedItem);
						recipeResultSlot.findIcon();
						recipeSlot1.setItem(null);
						recipeSlot2.setItem(null);
						recipeSlot3.setItem(null);
						recipeSlot1.findIcon();
						recipeSlot2.findIcon();
						recipeSlot3.findIcon();
					}
				} catch(NullPointerException e) {
				}
			}
		});
		add(craftButton);
		
		closeButton = new Button();
		closeButton.setText("Close");
		closeButton.addCallback(new Runnable() {

			@Override
			public void run() {
				getGameState().changeState();
				
			}
			
		});
		add(closeButton);
	}
	
	
	
	@Override
	public int getPreferredInnerWidth() {
		return(slots[0].getPreferredWidth() + slotSpacing) * numSlotsX - slotSpacing;
	}



	@Override
	public int getPreferredInnerHeight() {
		return(slots[0].getPreferredHeight() + slotSpacing) * numSlotsY - slotSpacing;
	}

	

	@Override
	protected void layout() {
		int slotWidth = slots[0].getPreferredWidth();
		int slotHeight = slots[0].getPreferredHeight();
		
		for(int row = 0, y = getInnerY() + getHeight() / 2 - 30, i = 0; row < numSlotsY; row++) {
			for(int col = 0, 
					x = getInnerX() + getWidth() / 2 - ((numSlotsX / 2) * slotWidth) - (slotSpacing * (numSlotsX - 2) -10); 
					col < numSlotsX; col++,i++) {
				slots[i].adjustSize();
				slots[i].setPosition(x, y);
				x += slotWidth + slotSpacing;
			}
			y += slotHeight + slotSpacing;
		}
		
		recipeSlot1.adjustSize();
		recipeSlot1.setPosition(getInnerX() + getWidth() / 2 - slotWidth / 2,20);
		
		recipeSlot2.adjustSize();
		recipeSlot2.setPosition(getInnerX() + 50, 200);
		
		recipeSlot3.adjustSize();
		recipeSlot3.setPosition(getInnerX() + getWidth() - 100, 200);
		
		recipeResultSlot.adjustSize();
		recipeResultSlot.setPosition(getInnerX() + getWidth() / 2 - slotWidth / 2, 120);
		
		craftButton.adjustSize();
		craftButton.setPosition(getInnerX() + getWidth() / 2 - craftButton.getWidth() / 2, 215);
		
		closeButton.adjustSize();
		closeButton.setPosition(getInnerX()+ getWidth() - craftButton.getWidth() -2*slotSpacing, 50);
	}



	@Override
	protected void applyTheme(ThemeInfo themeInfo) {
		super.applyTheme(themeInfo);
		slotSpacing = themeInfo.getParameter("slotSpacing", 5);
	}



	void dragStarted(ItemSlot slot, Event evt) {
		if(slot.getItem() != null) {
			dragSlot = slot;
			dragging(slot, evt);
		}
	}
	
	void dragging(ItemSlot slot, Event evt) {
		if(dragSlot != null) {
			Widget w = getWidgetAt(evt.getMouseX(),evt.getMouseY());
			if(w instanceof ItemSlot) {
				setDropSlot((ItemSlot) w);
				System.out.println("Drop Slot: " + dropSlot.getItem());
			} else {
				setDropSlot(null);
			}
		}
	}
	



	void dragStopped(ItemSlot slot, Event evt) {
		if(dragSlot != null) {
			dragging(slot, evt);
			if(dropSlot != null && dropSlot.canDrop() && dropSlot != dragSlot) {
				dropSlot.setItem(dragSlot.getItem());
				dragSlot.setItem(null);
				dropSlot.findIcon();
				dragSlot.findIcon();
				System.out.println("Dropping");
			} 
			else if(!(contains(evt.getMouseX(), evt.getMouseY())) && dropSlot == null){
				inventory.remove(dragSlot.getItem());
				Entity.getPlayer().itemsPending.add(dragSlot.getItem());
				dragSlot.setItem(null);
				dragSlot.findIcon();
			}
			setDropSlot(null);
			dragSlot = null;
		}
	}

	private void setDropSlot(ItemSlot slot) {
		if(slot != dropSlot) {
			if(dropSlot != null) {
				dropSlot.setDropState(false, false);
			}
			dropSlot = slot;
			if(dropSlot != null) {
				dropSlot.setDropState(true, dropSlot == dragSlot || dropSlot.canDrop());
			}
		}
		
	}
	
	public void updateInventory() {
		for(int i = 0; i < slots.length; i++) {
			try {
				slots[i].setItem(this.inventory.get(i));
			} catch(NullPointerException e) {
				slots[i].setItem(new Item());
			}
		}
	}
	
	public void leave() {
		for(int i = 0; i < slots.length; i++) {
			inventory.set(i,slots[i].getItem());
		}
	}
	
	public InventoryState getGameState(){
		return invState;
	}
	
	private boolean contains(int x, int y){
		Rectangle rect=new Rectangle(this.getX(),this.getY(), getWidth(), getHeight());
		return rect.contains(x, y);
	}
	
	
}

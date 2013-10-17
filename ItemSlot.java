package com.cannon.basegame;

import de.matthiasmann.twl.AnimationState;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ParameterMap;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.Image;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;

public class ItemSlot extends Widget{
	
	public static final StateKey STATE_DRAG_ACTIVE = StateKey.get("dragActive");
	public static final StateKey STATE_DROP_OKAY = StateKey.get("dropOk");
	public static final StateKey STATE_DROP_BLOCKED = StateKey.get("dropBlocked");
	
	public interface DragListener {
	    public void dragStarted(ItemSlot slot, Event evt);
	    public void dragging(ItemSlot slot, Event evt);
	    public void dragStopped(ItemSlot slot, Event evt);
	}
	
	private Item item;
	private Image icon;
	private DragListener listener;
	private boolean dragActive;
	private ParameterMap icons;

	public ItemSlot() {
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public DragListener getListener() {
		return listener;
	}
	
	public void setListener(DragListener listener) {
		this.listener = listener;
	}
	
	public void setDropState(boolean drop, boolean ok) {
		AnimationState animationState = getAnimationState();
		animationState.setAnimationState(STATE_DROP_OKAY, drop && ok);
		animationState.setAnimationState(STATE_DROP_BLOCKED, drop && !ok);
	}
	
	@Override
    protected boolean handleEvent(Event evt) {
        if(evt.isMouseEventNoWheel()) {
             if(dragActive) {
                 if(evt.isMouseDragEnd()) {
                     if(listener != null) {
                         listener.dragStopped(this, evt);
                     }
                        dragActive = false;
                        getAnimationState().setAnimationState(STATE_DRAG_ACTIVE, false);
                    } else if(listener != null) {
                        listener.dragging(this, evt);
                    }
                } else if(evt.isMouseDragEvent()) {
                    dragActive = true;
                    getAnimationState().setAnimationState(STATE_DRAG_ACTIVE, true);
                    if(listener != null) {
                        listener.dragStarted(this, evt);
                    }
                }
                return true;
            }
            return super.handleEvent(evt);
        }
	
	@Override 
	protected void paintWidget(GUI gui) {
		if(!dragActive && icon != null) {
			icon.draw(getAnimationState(),getInnerX(),getInnerY(),getInnerWidth(),getInnerHeight());
		}
	}

	@Override
	protected void paintDragOverlay(GUI gui, int mouseX, int mouseY, int modifier) {
		if(icon != null) {
			final int innerWidth = getInnerWidth();
			final int innerHeight = getInnerHeight();
			icon.draw(getAnimationState(), mouseX - innerWidth/2, mouseY - innerHeight/2, innerWidth, innerHeight);
		}
	}

	@Override
	protected void applyTheme(ThemeInfo themeInfo) {
		super.applyTheme(themeInfo);
		icons = themeInfo.getParameterMap("icons");
		findIcon();
	}

	public void findIcon() {
		if(item == null || icons == null) {
			icon = null;
		} else {
			icon = icons.getImage(item.getName());
		}
	}

	public boolean canDrop() {
		return item == null;
	}
	
}

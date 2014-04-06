package com.cannon.basegame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Action {
	
	protected ArrayList<Method> actions;
	protected Actor actor;
	protected int attackTimer = 0;
	protected int cooldownTimer = 0;
	protected int delayTimer = 0;
	
	/**Restriction for each method called in act**/
	protected ActionRestrictions[] restrictions;
	
	public Action(Actor actor){
		actions = new ArrayList<Method>();
		this.actor = actor;
	}
	
	public void act(int delta){
		for(Method action: actions){
			try {
				action.invoke(this, delta);
				//System.out.println("invoked " + action.getName());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public abstract boolean onCollision(Entity entity);
	
	public String[] addActionTypes(String... actions) {
		String[] returnVars = new String[actions.length];
		int index = 0;
		for(String action: actions){
			try{
				this.actions.add( getClass().getDeclaredMethod(action, int.class) );
			} catch(NoSuchMethodException e){
				returnVars[index++] = action;
			}
		}
		returnVars = Arrays.copyOf(returnVars, index);
		return returnVars;
	}
	
	public void setActionRestrictions(ArrayList<Method> actions) {
		restrictions = new ActionRestrictions[actions.size()];
		for(int index = 0; index < actions.size(); index++){
			Method method = actions.get(index);
			ActionRestrictions instance = new ActionRestrictions(method.getName());
			HashMap<String, Integer> map = getRestrictions(method.getName());
			instance.setRange(map.get("range"));
			instance.setTimes(map.get("attackTime"), map.get("cooldownTime"), map.get("delayTime"));
			instance.setInhibitsMovement(map.get("inhibitsMovement"));
			instance.setPriority(map.get("priority"));
			if(actor instanceof Enemy){
				instance.setTarget(Entity.getPlayer());
			}
			restrictions[index] = instance;
		}
		
		restrictions = ActionRestrictions.sortByPriority(restrictions);
	}
	
	protected boolean timersAreDone(int delta) {
		if(attackTimer > 0){
			actor.attacking = true;
			actor.canControlMovement = false;
			attackTimer -= delta;
			return false;
		}
		if(cooldownTimer > 0){
			actor.attacking = false;
			actor.canControlMovement = true;
			cooldownTimer -= delta;
			return false;
		}
		actor.actionFlag = false;
		return true;
	}

	protected void setTimers(int attackTimer, int cooldownTimer){
		this.attackTimer = attackTimer;
		this.cooldownTimer = cooldownTimer;
	}
	

	public String toString(){
		return actions.toString();
	}
	
	public HashMap<String, Integer> getRestrictions(String actionName){
		
		return null;
	}
	
}

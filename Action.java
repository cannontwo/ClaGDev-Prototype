package com.cannon.basegame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public abstract class Action {
	
	protected ArrayList<Method> actions;
	protected Actor actor;
	
	public Action(Actor actor){
		actions = new ArrayList<Method>();
		this.actor = actor;
	}
	
	public void act(){
		for(Method action: actions){
			try {
				action.invoke(this);
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
				this.actions.add( getClass().getDeclaredMethod(action) );
			} catch(NoSuchMethodException e){
				returnVars[index++] = action;
			}
		}
		returnVars = Arrays.copyOf(returnVars, index);
		return returnVars;
	}
	
	public static boolean secondHasPassed(Calendar since){
		Calendar now = Calendar.getInstance();
		if( (now.get(Calendar.MILLISECOND) > since.get(Calendar.MILLISECOND) && now.get(Calendar.SECOND) > since.get(Calendar.SECOND))){
			return true;
		}
		return ( (now.get(Calendar.SECOND) - since.get(Calendar.SECOND)) >= 2||
					(now.get(Calendar.MINUTE) > since.get(Calendar.MINUTE)) ||
					(now.get(Calendar.HOUR) > since.get(Calendar.HOUR)) ||
					(now.get(Calendar.DAY_OF_YEAR) > since.get(Calendar.DAY_OF_YEAR)) ||
					(now.get(Calendar.YEAR) > since.get(Calendar.YEAR)) );
	}
	
	public String toString(){
		return actions.toString();
	}
	
	
	
}

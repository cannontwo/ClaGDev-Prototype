package com.cannon.basegame;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**Controls when actions should occur for the AI and sets the timers for each action**/

public class ActionRestrictions {
	
	//Corresponds to the name of the method the restriction applies to
	public String name;
	
	//The range of the attack
	public int range = 0;
	//What the entity wants to attack
	public Entity target = null;
	//How long the attack takes
	public int attackTime = 0;
	//How long it takes to recover after the attack
	public int cooldownTime = 0;
	//How long it takes for the AI to "react"
	public int delayTime = 0;
	//Does the action inhibit movement?
	public boolean inhibitsMovement = true;
	
	//If there are multiple actions, which should go first?  The one with the highest priority
	public int priority = 0;
	
	public ActionRestrictions(String name) {
		this.name = name;
		
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void setTarget(Entity entity){
		this.target = entity;
	}
	
	public void setTimes(int aTime, int cTime, int dTime){
		attackTime = aTime;
		cooldownTime = cTime;
		delayTime = dTime;
	}
	
	public void setInhibitsMovement(int bool){
		if(bool == 1){
			inhibitsMovement = true;
		}else {
			inhibitsMovement = false;
		}
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public Object get(String field){
		Field[] fields = getClass().getDeclaredFields();
		for(Field field1: fields){
			if(field1.getName().equals(field)){
				try {
					return field1.get(this);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return null;
	}

	public void tryAction(Action action){
		
	}
	
	/**Returns a sorted list with the highest priority first**/
	public static ActionRestrictions[] sortByPriority(ActionRestrictions[] list){
		//I'm doing the bubble sort because that's the only one I remember
		for(int a = 0; a < list.length; a++){
			for(int b = 1; b < list.length; b++){
				if(a < b){
					ActionRestrictions temp = list[a];
					list[a] = list[b];
					list[b] = temp;
				}
			}
		}
		return list;
	}
	
}

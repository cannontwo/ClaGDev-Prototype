package com.cannon.basegame;


import java.util.HashMap;

public class MeleeAction extends Action {

	public static HashMap<Integer, String> meleeActionTypes;

	
	public MeleeAction(Actor actor){
		super(actor);
	}


	@Override
	public boolean onCollision(Entity entity) {
		if(attackTimer > 0){
			actor.hit(entity);
		}
		return true;
	}
	
	
	//should be passive, but i needed something to test
	public void jump(int delta){
		if(!timersAreDone(delta)){
			return;
		}
		actor.jump();

		setTimers(500, 500);
	}

	public void swipe(int delta){
		
		if(!timersAreDone(delta)){
			return;
		}
		
		for(Entity entity: Entity.entityList){
			if(entity == actor){
				continue;
			}
			if(actor.faceLeft){
				if(actor.getDistance(entity.x + entity.width, entity.y) < 50){
					if(actor.x > entity.x){
						actor.hit(entity);
						setTimers(500, 2500);
					}
				}
			}
			else{
				if(actor.getDistance(entity.x, entity.y) < 50){
					if(actor.x < entity.x){
						actor.hit(entity);
						setTimers(500, 2500);
					}
				}
			}
		}
		

	}

	
	
	

}

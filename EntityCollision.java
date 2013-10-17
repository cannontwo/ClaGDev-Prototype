package com.cannon.basegame;

import java.util.Stack;

public class EntityCollision {

	public static Stack<EntityCollision> entityCollisionList = new Stack<EntityCollision>();
	public Entity a;
	public Entity b;

	public EntityCollision(Entity entityA, Entity entityB) {
		this.a = entityA;
		this.b = entityB;
	}
}

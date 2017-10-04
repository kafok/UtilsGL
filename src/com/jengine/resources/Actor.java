package com.jengine.resources;

import com.jengine.math.Vector2d;

public abstract class Actor {

	private Vector2d position;

	public Actor() {
		super();
		this.position = new Vector2d();
	}

	public Vector2d getPosition() {
		return position;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}
	
	
	public abstract void update(double delta);
	public abstract void render();
	public abstract void collide(Actor actor);
}

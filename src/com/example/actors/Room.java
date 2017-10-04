package com.example.actors;

import com.jengine.core.App;
import com.jengine.math.Vector2d;
import com.jengine.resources.Actor;
import com.utilsgl.render.Color;
import com.utilsgl.render.ColorRGB;
import com.utilsgl.utils.Render2D;

public class Room extends Actor {
	
	public Room() {
		super();
	}
	

	@Override
	public void update(double delta) {
	}

	@Override
	public void render() {
	}

	@Override
	public void collide(Actor actor) {
//		if(actor instanceof Solid) {
//			Solid b = (Solid) actor;
//			
//			double dist = radix + b.radix;
//			if(getPosition().squareDistance(b.getPosition()) <= dist*dist) {
////				Color swap = color;
////				color = sColor;
////				sColor = swap;
//				
//				Vector2d dir = new Vector2d(b.getVelocity());
//				dir.normalize();
//				
//				velocity.setX(velocity.getX() + dir.getX()*velocityFactor);
//				velocity.setY(velocity.getY() + dir.getY()*velocityFactor);
//			}
//		}
		
		double apx = actor.getPosition().getX();
		double apy = actor.getPosition().getY();
		
		Bola b = null;
		if(actor instanceof Bola) {
			b = (Bola) actor;
		}
		
		if(apx < 0) {
			actor.getPosition().setX(0.);
			if(b != null)
				b.getVelocity().setX(-b.getVelocity().getX());
		}
		if(apy < 0) {
			actor.getPosition().setY(0.);
			if(b != null)
				b.getVelocity().setY(-b.getVelocity().getY());
		}
		if(apx > App.get().getWindow().getWidth()) {
			actor.getPosition().setX((double) App.get().getWindow().getWidth());
			if(b != null)
				b.getVelocity().setX(-b.getVelocity().getX());
		}
		if(apy > App.get().getWindow().getHeight()) {
			actor.getPosition().setY((double) App.get().getWindow().getHeight());
			if(b != null)
				b.getVelocity().setY(-b.getVelocity().getY());
		}
	}

}

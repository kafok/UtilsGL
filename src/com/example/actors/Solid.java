package com.example.actors;

import com.jengine.math.Vector2d;
import com.jengine.resources.Actor;
import com.utilsgl.render.Color;
import com.utilsgl.render.ColorRGB;
import com.utilsgl.utils.Render2D;

public class Solid extends Actor {
	
	private Color color;
	private int radix;
	
	public Solid(Color color, int radix) {
		super();
		this.color = color;
		this.radix = radix;
	}
	

	@Override
	public void update(double delta) {
	}

	@Override
	public void render() {
		float x = (float) (getPosition().getX() - radix);
		float y = (float) (getPosition().getY() - radix);
		Render2D.renderRectangle(x, y, x+2*radix, y+2*radix, color);
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
		double ix = getPosition().getX() - radix;
		double iy = getPosition().getY() - radix;
		double fx = ix+2*radix;
		double fy = iy+2*radix;
	}

}

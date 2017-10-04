package com.example.actors;

import com.jengine.math.Vector2d;
import com.jengine.resources.Actor;
import com.utilsgl.render.Color;
import com.utilsgl.render.ColorRGB;
import com.utilsgl.utils.Render2D;

public class Bola extends Actor {
	
	private Color color;
	private Color sColor;
	private int radix;
	private double factor;
	
	protected Vector2d velocity;
	protected double velocityFactor;
	
	public Bola(Color color, int radix) {
		super();
		this.color = color;
		this.sColor = new ColorRGB(0, 0, 0);
		this.radix = radix;
		
		velocity = new Vector2d();
		velocityFactor = 0.01;
		factor = 0.006;
	}
	
	
	public Vector2d getVelocity() {
		return velocity;
	}
	

	@Override
	public void update(double delta) {
		velocity.setX(velocity.getX() - factor*velocity.getX()*delta);
		velocity.setY(velocity.getY() - factor*velocity.getY()*delta);
		
		getPosition().setX(getPosition().getX() + velocity.getX()*delta);
		getPosition().setY(getPosition().getY() + velocity.getY()*delta);
		
//		getPosition().setX(velocity.getX());
//		getPosition().setY(velocity.getY());
	}

	@Override
	public void render() {
		Render2D.renderCircle(getPosition().getX().floatValue(), getPosition().getY().floatValue(), radix, color);
	}

	@Override
	public void collide(Actor actor) {
		if(actor instanceof PlayerBola) {
			Bola b = (Bola) actor;
			
			double dist = radix + b.radix;
			if(getPosition().squareDistance(b.getPosition()) <= dist*dist) {
//				Color swap = color;
//				color = sColor;
//				sColor = swap;
				
				Vector2d dir = new Vector2d(b.getVelocity());
				dir.normalize();
				
				velocity.setX(velocity.getX() + dir.getX()*velocityFactor);
				velocity.setY(velocity.getY() + dir.getY()*velocityFactor);
			}
		}
	}

}

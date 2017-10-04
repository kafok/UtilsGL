package com.example.actors;

import com.jengine.core.App;
import com.jengine.resources.Actor;
import com.utilsgl.input.Key;
import com.utilsgl.render.Color;

public class PlayerBola extends Bola {
	
	public PlayerBola(Color color, int radix, double velocityFactor) {
		super(color, radix);
		this.velocityFactor = velocityFactor;
	}
	

	@Override
	public void update(double delta) {
		velocity.setX(0.);
		velocity.setY(0.);
		
		if(App.get().getWindow().isPressed(Key.A))
			velocity.setX(-velocityFactor);
		if(App.get().getWindow().isPressed(Key.S))
			velocity.setY(velocityFactor);
		if(App.get().getWindow().isPressed(Key.D))
			velocity.setX(velocityFactor);
		if(App.get().getWindow().isPressed(Key.W))
			velocity.setY(-velocityFactor);
		
		getPosition().setX(getPosition().getX() + velocity.getX()*delta);
		getPosition().setY(getPosition().getY() + velocity.getY()*delta);
	}
	
	@Override
	public void collide(Actor actor) {
	}

}

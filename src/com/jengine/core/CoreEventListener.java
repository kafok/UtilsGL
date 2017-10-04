package com.jengine.core;

import java.util.Random;

import com.example.actors.Bola;
import com.example.actors.PlayerBola;
import com.example.actors.Room;
import com.example.actors.Solid;
import com.jengine.resources.Actor;
import com.jengine.resources.Scene;
import com.utilsgl.event.WindowEvent;
import com.utilsgl.render.ColorRGB;
import com.utilsgl.utils.Render2D;
import com.utilsgl.utils.UtilsGL;

public class CoreEventListener extends WindowEvent {

	private Scene scene;
	private int frames;
	private long second;
	
	public CoreEventListener(){
		scene = new Scene();
		second = System.currentTimeMillis();
		
		Random rnd = new Random(12);
		for(int i=0; i<5; i++) {
			Actor act = new Bola(new ColorRGB(255, 0, 0), 30);
			act.getPosition().setX((double) rnd.nextInt(App.get().getWindow().getWidth()));
			act.getPosition().setY((double) rnd.nextInt(App.get().getWindow().getHeight()));
			
			Actor act2 = new Solid(new ColorRGB(0, 0, 255), 30);
			act2.getPosition().setX((double) rnd.nextInt(App.get().getWindow().getWidth()));
			act2.getPosition().setY((double) rnd.nextInt(App.get().getWindow().getHeight()));
			
			scene.getActors().add(act);
			scene.getActors().add(act2);
		}
		
		scene.getActors().add(new PlayerBola(new ColorRGB(0, 255, 0), 15, 1));
		scene.getActors().add(new Room());
	}
	
	
	public void onIdle() {
		UtilsGL.calculeDelta();
		double delta = UtilsGL.getDelta();
		
		if(System.currentTimeMillis() - second >= 1000) {
			App.get().getWindow().setTitle(frames + " FPS");
			second = System.currentTimeMillis();
			frames = 0;
		}
		
		Render2D.clear(scene.getBackground());
		Render2D.set2DMode(App.get().getWindow().getWidth(), App.get().getWindow().getHeight());
		
		for(Actor actor : scene.getActors()) {
			
			// Do
			actor.update(delta);
			
			// Collision
			for(Actor other : scene.getActors()) {
				if(actor == other)
					continue;
				
				actor.collide(other);
			}
			
			actor.render();
		}

		frames++;
	}
}

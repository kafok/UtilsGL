package com.jengine.resources;

import java.util.LinkedList;
import java.util.List;

import com.utilsgl.render.Color;
import com.utilsgl.render.ColorRGB;

public class Scene {

	private List<Actor> actors;
	private Color background;

	public Scene() {
		super();
		this.actors = new LinkedList<Actor>();
		this.background = new ColorRGB(255, 255, 255);
	}

	
	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public List<Actor> getActors() {
		return actors;
	}
}

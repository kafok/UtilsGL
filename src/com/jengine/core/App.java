package com.jengine.core;

import com.utilsgl.secreen.gui.Window;

public class App {

	private static App instance;
	public static App get() {
		if(instance == null)
			instance = new App();
		
		return instance;
	}
	
	
	private Window window;
	
	public App() {
		super();
	}
	

	public Window getWindow() {
		return window;
	}

	void setWindow(Window window) {
		this.window = window;
	}
	
	
	
}

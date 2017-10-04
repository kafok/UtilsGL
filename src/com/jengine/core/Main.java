package com.jengine.core;

import com.utilsgl.secreen.Context;
import com.utilsgl.secreen.Monitor;
import com.utilsgl.secreen.gui.Window;
import com.utilsgl.utils.UtilsGL;

public class Main {

	public static void main(String[] args) {
		UtilsGL.init("./natives");

		App.get().setWindow(new Window(new Context()));

		Monitor mon = Monitor.getMonitors().get(0);
		mon.setVideoMode(mon.getVideoModes().get(0));

		App.get().getWindow().create(800, 600, "Unknow FPS");
		App.get().getWindow().makeCurrentContext();

		App.get().getWindow().registerEvent(new CoreEventListener());
		App.get().getWindow().swapInterval(0);

		App.get().getWindow().live();
	}

}
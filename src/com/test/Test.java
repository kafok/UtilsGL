package com.test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.render.font.FontRender;
import com.screen.Context;
import com.screen.Monitor;
import com.screen.gui.Window;
import com.utils.UtilsGL;

//TAREAS
//TODO Task: Renderizar texto, configurando sus fonts (estilos varios) y personalizando los caracteres UTF
//TODO Task: Herencia de imageData
//TODO Task: Render en swing
//TODO Task: Application
//TODO Task: Render 3D
//TODO Task: Refactorizar clases 2D

public class Test {
	public static Window win;

	public static List<Integer> texID;
	public static FontRender fr;

	public static void main(String[] args) throws IOException {
		UtilsGL.init("./natives");

		win = new Window(new Context().setSamples(0));

		Monitor mon = Monitor.getMonitors().get(0);
		mon.setVideoMode(mon.getVideoModes().get(0));

		win.create(800, 600, "Unknow FPS");
		win.makeCurrentContext();

		/*********************/
		fr = new FontRender("Times New Roman", 90);
		getTexID(fr);

		/*********************/

		win.registerEvent(new Even());
		win.swapInterval(0);

		win.live();
	}

	public static void getTexID(FontRender fr) {
		List<Integer> res = new LinkedList<Integer>();

		for (FontRender.Glyph g : fr.glyphs) {
			if (!res.contains(g.getTexture()))
				res.add(g.getTexture());
		}

		System.out.println(res.size());
		texID = res;
	}

	public static void muestraTextura(int num) {
		// Render2D.renderImage(0, 0, 1024, 1024, new Texture(texID.get(num)));
		fr.render(0, 0, '!');

	}

}

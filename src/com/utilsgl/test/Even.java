package com.utilsgl.test;

import java.io.IOException;

import com.utilsgl.event.WindowEvent;
import com.utilsgl.render.ColorRGB;
import com.utilsgl.render.image.Texture;
import com.utilsgl.utils.Render2D;
import com.utilsgl.utils.UtilsGL;

public class Even extends WindowEvent {
	float rot;

	float x;
	float y;

	float zoom;
	float camx;
	float camy;

	Texture tex;

	public Even() throws IOException {
		rot = 0.0f;

		x = 0.f;
		y = 0.f;

		zoom = 1.f;
		camx = 0.f;
		camy = 0.f;

		// tex = new Texture(new ImageDataByteBuffer("uno.png"), Texture.NEAREST);

		// GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// Font[] fonts = e.getAllFonts(); // Get the fonts
		// for (Font f : fonts) {
		// System.out.println(f.getFontName());
		// }

		// FontRange fr = new FontRange();
		// fr.addASCIIRange();
		//
		// int i = 0;
		// FontRender frender = new FontRender(new Font("Times New Roman", Font.PLAIN,
		// 24), fr);
		// for(BufferedImage bi : bia) {
		// ImageIO.write(bi, "PNG", new File("glyph/glyph-" + i + ".png"));
		// i++;
		// }

		// ImageData img = new ImageDataByteBuffer("glyph.png");
		// ImageData img = new ImageDataByteBuffer(bia[0]);

		// for(ImageData.Pixel pixel : img) {
		//// if(pixel.get() > 0)
		//// pixel.set(0);
		//// else
		//// pixel.set(0xFFFFFFFF);
		//// if(pixel.getAlpha() < 0xFF)
		//// pixel.set(0);
		//// else
		//// pixel.set(0xFFFFFFFF);
		////
		//// pixel.setAlpha(0xFF);
		// }

		// img.save("glyph2.png");

		// tex = new Texture(img, Texture.LINEAR);
	}

	public void onIdle() {
		UtilsGL.calculeDelta();
		Test.win.setTitle(UtilsGL.getFPS() + " FPS");

		Render2D.clear(); // TODO Doc: Borra con el ultimo color borrado
		Render2D.clear(new ColorRGB(0xFFFFFF));
		Render2D.set2DMode(Test.win.getWidth(), Test.win.getHeight());

		// Render2D.renderRectangle(0.f, 0.f, 500.f, 500.f, new ColorRGB(0x999999));
		//
		// Test.muestraTextura(0);

		// Render2D.renderImage(0.f, 0.f, 3000.f, 3000.f, tex);
		// Render2D.renderImage(0.f, 0.f, tex);

	}
}

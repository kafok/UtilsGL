package com.utilsgl.render.image;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

import com.utilsgl.render.image.ImageData.Pixel;
import com.utilsgl.secreen.Renderable;

public abstract class ImageData implements Iterable<Pixel> {
	
	
	//consultores -----------------------------------------
	
	public abstract byte[] getData();
	
	public abstract ByteBuffer getDataAsByteBuffer();

	public abstract int getWidth();
	public abstract int getHeight();
	public abstract boolean hasAlpha();
	
	
	//opereaciones sobre pixeles ---------------------------
	
	public abstract void setPixel(int x, int y, int color);
	public abstract void setRedPixel(int x, int y, int red);
	public abstract void setGreenPixel(int x, int y, int green);
	public abstract void setBluePixel(int x, int y, int blue);
	public abstract void setAlphaPixel(int x, int y, int alpha);
	
	
	public abstract int getPixel(int x, int y);
	public abstract int getRedPixel(int x, int y);
	public abstract int getGreenPixel(int x, int y);
	public abstract int getBluePixel(int x, int y);
	public abstract int getAlphaPixel(int x, int y);
	
	
	//utils --------------------------------------------------
	
	public abstract void save(String path) throws IOException;
	
	
	//iterable -----------------------------------------------
	
	public class Pixel {
		
		private int x;
		private int y;
		
		
		protected Pixel(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getRed() {
			return getRedPixel(x, y);
		}
		
		public void setRed(int red) {
			setRedPixel(x, y, red);
		}
		
		public int getGreen() {
			return getGreenPixel(x, y);
		}
		
		public void setGreen(int green) {
			setGreenPixel(x, y, green);
		}
		
		public int getBlue() {
			return getBluePixel(x, y);
		}
		
		public void setBlue(int blue) {
			setBluePixel(x, y, blue);
		}
		
		public int getAlpha() {
			return getAlphaPixel(x, y);
		}
		
		public void setAlpha(int alpha) {
			setAlphaPixel(x, y, alpha);
		}
		
		public int get() {
			return getPixel(x, y);
		}
		
		public void set(int pixel) {
			setPixel(x, y, pixel);
		}
	}
	
	
	private class PixelIterator implements Iterator<Pixel> {

		private int x;
		private int y;
		
		public PixelIterator() {
			this.x = 0;
			this.y = 0;
		}
		
		public boolean hasNext() {
			return x != (getWidth()) || y != (getHeight()-1);
		}
		
		public Pixel next() {
			
			if(x == getWidth()) {
				x = 0;
				y++;
			}
			
			return new Pixel(x++, y);
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public Iterator<Pixel> iterator() {
		return new PixelIterator();
	}
	
	
	//statics ------------------------------------------------
	
	public static ImageData fromRenderable(Renderable render) {
		ImageDataByteBuffer res = new ImageDataByteBuffer();
		res.width = render.getWidth();
		res.height = render.getHeight();
		res.alpha = render.getContext().getDephtBits()/8 == 4;
		
		int num = res.alpha ? 4 : 3;
		
		ByteBuffer data = render.getAsByteBuffer();
		ByteBuffer clone = ByteBuffer.allocateDirect(data.capacity());
		for(int x=0; x<res.width; x++) {
			for(int y=0; y<res.height; y++) {
				for(int a=0; a<num; a++) {
					byte col = data.get(num*(y*res.width + x) + a);
					clone.put(num*((res.height - y - 1)*res.width + x) + a, col);
				}
			}
		}
		clone.rewind();
		
		res.data = clone;
		
		return res;
	}
	
	
	
}

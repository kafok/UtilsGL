package com.render.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import de.matthiasmann.twl.utils.PNGDecoder;

public class ImageDataByteBuffer extends ImageData {
	
	//atributos -------------------------------------------
	
	ByteBuffer data;
	int width;
	int height;
	boolean alpha;
	
	
	//constructor -----------------------------------------
	
	ImageDataByteBuffer() {
	}
	
	public ImageDataByteBuffer(int width, int height, boolean alpha) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("The minimum size of the image is 1x1");
		
		if(alpha)
			this.data = ByteBuffer.allocateDirect(this.width * this.height * 4);
		else
			this.data = ByteBuffer.allocateDirect(this.width * this.height * 3);
	}

	public ImageDataByteBuffer(int width, int height, boolean alpha, byte[] data) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("The minimum size of the image is 1x1");
		
		int numColor = alpha ? 4 : 3;
		this.data = ByteBuffer.allocateDirect(this.width * this.height * numColor);
		
		for(int i=0; i<data.length; i++) {
			this.data.put(i, data[i]);
		}
	}
	
	public ImageDataByteBuffer(ImageData imgageData) {
		this.data = imgageData.getDataAsByteBuffer();
		this.width = imgageData.getWidth();
		this.height = imgageData.getHeight();
		this.alpha = imgageData.hasAlpha();
	}
	
	public ImageDataByteBuffer(BufferedImage bufferedImage) {
		this.width = bufferedImage.getWidth();
		this.height = bufferedImage.getHeight();
		this.alpha = bufferedImage.getAlphaRaster() != null;
		
		this.data = convertToArray(bufferedImage);
	}
	
	public ImageDataByteBuffer(String path) throws IOException {
		FileInputStream in = new FileInputStream(path);
        PNGDecoder decoder = new PNGDecoder(in);
        
        this.alpha = decoder.hasAlpha();
		this.width = decoder.getWidth();
		this.height = decoder.getHeight();
		
		int numColor = this.alpha ? 4 : 3;
        ByteBuffer buf = ByteBuffer.allocateDirect(numColor*decoder.getWidth()*decoder.getHeight());
        decoder.decode(buf, decoder.getWidth()*numColor, this.alpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB);
        buf.flip();
        in.close();
        
        this.data = buf;
	}
	
	public ImageDataByteBuffer(int width, int height, boolean alpha, ByteBuffer buf) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		ByteBuffer clone = ByteBuffer.allocateDirect(buf.capacity());
		buf.rewind();
		clone.put(buf);
		buf.rewind();
		
		this.data = clone;
	}
	
	
	//consultores -----------------------------------------
	
	public byte[] getData() {
		int numColor = alpha ? 4 : 3;
		byte[] res = new byte[this.width*this.height*numColor];
		
		int count = 0;
		for(int i=0; i<this.data.capacity(); i++) {
			res[count] = this.data.get(i);
		}
		
		return res;
	}
	
	public ByteBuffer getDataAsByteBuffer() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean hasAlpha() {
		return alpha;
	}
	
	
	//opereaciones sobre pixeles ---------------------------
	
	public void setPixel(int x, int y, int color) {
		int numColor = alpha ? 4 : 3;
		
		data.put(numColor*(y*this.width + x), (byte) ((color&0xFF0000)>>>16));
		data.put(numColor*(y*this.width + x) + 1, (byte) ((color&0xFF00)>>>8));
		data.put(numColor*(y*this.width + x) + 2, (byte) (color&0xFF));
	}
	
	public void setRedPixel(int x, int y, int red) {
		int numColor = alpha ? 4 : 3;
		data.put(numColor*(y*this.width + x), (byte) (red&0xFF));
	}
	
	public void setGreenPixel(int x, int y, int green) {
		int numColor = alpha ? 4 : 3;
		data.put(numColor*(y*this.width + x) + 1, (byte) (green&0xFF));
	}
	
	public void setBluePixel(int x, int y, int blue) {
		int numColor = alpha ? 4 : 3;
		data.put(numColor*(y*this.width + x) + 2, (byte) (blue&0xFF));
	}
	
	public void setAlphaPixel(int x, int y, int alpha) {
		if(this.alpha) {
			data.put(4*(y*this.width + x) + 3, (byte) (alpha&0xFF));
		} else {
			throw new IllegalArgumentException("Image has not alpha channel");
		}
	}
	
	
	public int getPixel(int x, int y) {
		int numColor = alpha ? 4 : 3;
		int pos = numColor*(y*this.width + x);
		return ((int)(data.get(pos)&0xFF) << 16) | ((int)(data.get(pos + 1)&0xFF) << 8) | ((int)data.get(pos + 2)&0xFF);
	}
	
	public int getRedPixel(int x, int y) {
		int numColor = alpha ? 4 : 3;
		return data.get(numColor*(y*this.width + x))&0xFF;
	}
	
	public int getGreenPixel(int x, int y) {
		int numColor = alpha ? 4 : 3;
		return data.get(numColor*(y*this.width + x) + 1)&0xFF;
	}
	
	public int getBluePixel(int x, int y) {
		int numColor = alpha ? 4 : 3;
		return data.get(numColor*(y*this.width + x) + 2)&0xFF;
	}
	
	public int getAlphaPixel(int x, int y) {
		if(this.alpha) {
			return data.get(4*(y*this.width + x) + 3)&0xFF;
		} else {
			return 255;
		}
	}
	
	
	//utils -------------------------------------------------------
	
	public void save(String path) throws IOException {
		String []as = path.split("[.]");
		String format = as[as.length-1].toUpperCase();
		File file = new File(path);
		
		boolean formatAlpha = (format.equals("PNG") || format.equals("GIF"));
		BufferedImage image = new BufferedImage(width, height, (alpha && formatAlpha) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
		
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				int col;
				if(alpha)	//si la imagen tiene canal alfa
					if(formatAlpha)	//si el formato de salida admite transparecias
						col = (this.getAlphaPixel(i, j) << 24) | this.getPixel(i, j);
					else
						if(this.getAlphaPixel(i, j) < 255)	//si el formato no admite transparencias y hay pixeles con alfa != alfa maxima
							col = 0;	//negro para colore transparentes
						else
							col = this.getPixel(i, j);
				else
					col = this.getPixel(i, j);
					
				image.setRGB(i, j, col);
			}
		}
		
		ImageIO.write(image, format, file);
	}
	
	
	//utils -------------------------------------------------------
	
	private static ByteBuffer convertToArray(BufferedImage image) {
		int type = image.getType();
		ByteBuffer res = null;
		
		final int width = image.getWidth();
		final int height = image.getHeight();
		
		switch(type) 
		{
			case BufferedImage.TYPE_3BYTE_BGR:
				final byte[] pixels1 = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
				res = ByteBuffer.allocateDirect(width * height * 3);
				res = convertToArray8ABGR(pixels1, false, res);
				break;
				
			case BufferedImage.TYPE_4BYTE_ABGR:
				final byte[] pixels2 = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
				res = ByteBuffer.allocateDirect(width * height * 4);
				convertToArray8ABGR(pixels2, true, res);
				break;
				
			case BufferedImage.TYPE_INT_ARGB:
				final int[] pixels3 = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
				res = ByteBuffer.allocateDirect(width * height * 4);
				res = convertToArray32ARGB(pixels3, true, res);
				break;
				
			case BufferedImage.TYPE_INT_RGB:
				final int[] pixels4 = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
				res = ByteBuffer.allocateDirect(width * height * 3);
				res = convertToArray32ARGB(pixels4, false, res);
				break;
		}
		
		return res;
	}
	
	private static ByteBuffer convertToArray8ABGR(byte[] pixels, boolean alpha, ByteBuffer res) {
		if(alpha) {
			final int pixelLength = 4;
			for(int pixel = 0; pixel < pixels.length; pixel += pixelLength) {
				res.put(pixel, pixels[pixel + 3]);
				res.put(pixel + 1, pixels[pixel + 2]);
				res.put(pixel + 2, pixels[pixel + 1]);
				res.put(pixel + 3, pixels[pixel]);
			}
		} else {
			final int pixelLength = 3;
			for(int pixel = 0; pixel < pixels.length; pixel += pixelLength) {
				res.put(pixel, pixels[pixel + 2]);
				res.put(pixel + 1, pixels[pixel + 1]);
				res.put(pixel + 2, pixels[pixel]);
			}
		}
		
		return res;
	}
	
	private static ByteBuffer convertToArray32ARGB(int[] pixels, boolean alpha, ByteBuffer res) {
		if(alpha) {
			for(int pixel = 0; pixel < pixels.length; pixel++) {
				res.put(4*pixel, (byte) ((pixels[pixel]&0xFF0000)>>>16));
				res.put(4*pixel + 1, (byte) ((pixels[pixel]&0xFF00)>>>8));
				res.put(4*pixel + 2, (byte) ((pixels[pixel]&0xFF)));
				res.put(4*pixel + 3, (byte) ((pixels[pixel]&0xFF000000)>>>24));
			}
		} else {
			for(int pixel = 0; pixel < pixels.length; pixel++) {
				res.put(4*pixel, (byte) ((pixels[pixel]&0xFF0000)>>>16));
				res.put(4*pixel + 1, (byte) ((pixels[pixel]&0xFF00)>>>8));
				res.put(4*pixel + 2, (byte) ((pixels[pixel]&0xFF)));
			}
		}
		
		return res;
	}
	
//	private static byte normalizeInt(int i) {
//		long ui = i;
//		ui = ui&0xFFFFFFFFL;
//		double rate = 5.937181414556033E-8;
//		return (byte) (Math.round(ui*rate));
//	}
	
}

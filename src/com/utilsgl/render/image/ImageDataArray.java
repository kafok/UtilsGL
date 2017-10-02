package com.utilsgl.render.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import de.matthiasmann.twl.utils.PNGDecoder;

public class ImageDataArray extends ImageData {
	
	//atributos -------------------------------------------
	
	private byte[][][] data;
	private int width;
	private int height;
	private boolean alpha;
	
	
	//constructor -----------------------------------------
	
	public ImageDataArray(int width, int height, boolean alpha) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("The minimum size of the image is 1x1");
		
		if(alpha)
			this.data = new byte[this.width][this.height][4];
		else
			this.data = new byte[this.width][this.height][3];
	}

	public ImageDataArray(int width, int height, boolean alpha, byte[] data) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("The minimum size of the image is 1x1");
		
		int numColor = alpha ? 4 : 3;
		this.data = new byte[this.width][this.height][numColor];
		
		int count = 0;
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				for(int k=0; k<numColor; k++) {
					this.data[i][j][k] = data[count];
					count++;
				}
			}
		}
	}
	
	public ImageDataArray(ImageData imgageData) {
		this.width = imgageData.getWidth();
		this.height = imgageData.getHeight();
		this.alpha = imgageData.hasAlpha();
		
		int numColor = alpha ? 4 : 3;
		this.data = new byte[this.width][this.height][numColor];
		
		int count = 0;
		byte[] data = imgageData.getData();
		
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				for(int k=0; k<numColor; k++) {
					this.data[i][j][k] = data[count];
					count++;
				}
			}
		}
	}
	
	public ImageDataArray(BufferedImage bufferedImage) {
		this.width = bufferedImage.getWidth();
		this.height = bufferedImage.getHeight();
		this.alpha = bufferedImage.getAlphaRaster() != null;
		
		this.data = convertToArray(bufferedImage);
	}
	
	public ImageDataArray(String path) throws IOException {
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
        
        this.data = new byte[this.width][this.height][numColor];
        int count = 0;
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				for(int k=0; k<numColor; k++) {
					byte num = buf.get(count);
					this.data[i][j][k] = num;
					count++;
				}
			}
		}
	}
	
	public ImageDataArray(int width, int height, boolean alpha, ByteBuffer buf) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		
		int numColor = this.alpha ? 4 : 3;
        
        this.data = new byte[this.width][this.height][numColor];
        int count = 0;
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				for(int k=0; k<numColor; k++) {
					byte num = buf.get(count);
					this.data[this.width - i - 1][j][k] = num;
					count++;
				}
			}
		}
	}
	
	
	//consultores -----------------------------------------
	
	public byte[] getData() {
		int numColor = alpha ? 4 : 3;
		byte[] res = new byte[this.width*this.height*numColor];
		
		int count = 0;
		for(int i=0; i<this.width; i++) {
			for(int j=0; j<this.height; j++) {
				for(int k=0; k<numColor; k++) {
					res[count] = this.data[i][j][k];
					count++;
				}
			}
		}
		
		return res;
	}
	
	public ByteBuffer getDataAsByteBuffer() {
		byte[] _data = getData();
		int numColor = this.alpha ? 4 : 3;
		
		ByteBuffer res = ByteBuffer.allocateDirect(this.width*this.height*numColor);
		res.rewind();
		
		for(int i=0; i<_data.length; i++)
			res.put(i, _data[i]);
			
		return res;
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
		data[x][y][0] = (byte) ((color&0xFF0000)>>>16);
		data[x][y][1] = (byte) ((color&0xFF00)>>>8);
		data[x][y][2] = (byte) (color&0xFF);
	}
	
	public void setRedPixel(int x, int y, int red) {
		if(alpha) {
			data[x][y][0] = (byte) ((red&0xFF000000)>>>24);
		} else {
			data[x][y][0] = (byte) ((red&0xFF0000)>>>16);
		}
	}
	
	public void setGreenPixel(int x, int y, int green) {
		if(alpha) {
			data[x][y][1] = (byte) ((green&0xFF0000)>>>16);
		} else {
			data[x][y][1] = (byte) ((green&0xFF00)>>>8);
		}
	}
	
	public void setBluePixel(int x, int y, int blue) {
		if(alpha) {
			data[x][y][2] = (byte) ((blue&0xFF00)>>>8);
		} else {
			data[x][y][2] = (byte) (blue&0xFF);
		}
	}
	
	public void setAlphaPixel(int x, int y, int alpha) {
		if(this.alpha) {
			data[x][y][3] = (byte) (alpha&0xFF);
		} else {
			throw new IllegalArgumentException("Image has not alpha channel");
		}
	}
	
	
	public int getPixel(int x, int y) {
		return (int) ((getRedPixel(x, y) << 16) | (getGreenPixel(x, y) << 8) | getBluePixel(x, y));
	}
	
	public int getRedPixel(int x, int y) {
		return data[x][y][0]&0xFF;
	}
	
	public int getGreenPixel(int x, int y) {
		return data[x][y][1]&0xFF;
	}
	
	public int getBluePixel(int x, int y) {
		return data[x][y][2]&0xFF;
	}
	
	public int getAlphaPixel(int x, int y) {
		if(this.alpha) {
			return data[x][y][3]&0xFF;
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
						col = ((data[i][j][3]&0xFF) << 24) | ((data[i][j][0]&0xFF) << 16) | ((data[i][j][1]&0xFF) << 8) | (data[i][j][2]&0xFF);
					else
						if((data[i][j][3]&0xFF) < 255)	//si el formato no admite transparencias y hay pixeles con alfa != alfa maxima
							col = 0;	//negro para colore transparentes
						else
							col = ((data[i][j][0]&0xFF) << 16) | ((data[i][j][1]&0xFF) << 8) | (data[i][j][2]&0xFF);
				else
					col = ((data[i][j][0]&0xFF) << 16) | ((data[i][j][1]&0xFF) << 8) | (data[i][j][2]&0xFF);
					
				image.setRGB(j, i, col);
			}
		}
		
		ImageIO.write(image, format, file);
	}
	
	
	//utils -------------------------------------------------------
	
	private static byte[][][] convertToArray(BufferedImage image) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean alpha = image.getAlphaRaster() != null;
		
		byte[][][] res;
		if(alpha) {
			res = new byte[height][width][4];
			final int pixelLength = 4;
			for(int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				res[row][col][0] = pixels[pixel];
				res[row][col][1] = pixels[pixel + 1];
				res[row][col][2] = pixels[pixel + 2];
				res[row][col][3] = pixels[pixel + 3];
				col++;
				
				if(col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			res = new byte[height][width][3];
			final int pixelLength = 3;
			for(int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				res[row][col][0] = (byte) Math.abs(pixels[pixel]);
				res[row][col][1] = (byte) Math.abs(pixels[pixel + 1]);
				res[row][col][2] = (byte) Math.abs(pixels[pixel + 2]);
				col++;
				
				if(col == width) {
					col = 0;
					row++;
				}
			}
		}
		
		return res;
	}
	
}

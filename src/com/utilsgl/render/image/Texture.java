package com.utilsgl.render.image;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture 
{
	//filtro
	public static int LINEAR = GL_LINEAR;
	public static int NEAREST = GL_NEAREST;
	
	
	//atributos
	private int interpolation;
	private int id;
	
	private int width;
	private int height;
	private boolean alpha;
	
	
	//constructor
	public Texture(String path) throws IOException
	{
		this.interpolation = GL_LINEAR;
		create(path);
	}
	
	public Texture(ImageData img) throws IOException
	{
		this.interpolation = GL_LINEAR;
		create(img);
	}
	
	public Texture(ImageData img, int mode) throws IOException
	{
		this.interpolation = mode;
		create(img);
	}
	
	public Texture(String path, int mode) throws IOException
	{
		this.interpolation = mode;
		create(path);
	}
	
	public Texture(int id, int mode) throws IOException
	{
		interpolation = mode;
		this.id = id;
		
		width = -1;
		height = -1;
		alpha = true;
	}
	
	public Texture(int id)
	{
		interpolation = GL_LINEAR;
		this.id = id;
		
		width = -1;
		height = -1;
		alpha = true;
	}
	
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
		glEnable(GL_TEXTURE_2D);
		
		if(alpha) 
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, this.interpolation);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, this.interpolation);
	}
	
	
	public void unbind()
	{
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	
	public void destroy()
	{
		glDeleteTextures(id);
	}
	
	
	private void create(String path) throws IOException
	{
		IntBuffer ib = BufferUtils.createIntBuffer(1);
		glGenTextures(ib);
		this.id = ib.get(0);
		glBindTexture(GL_TEXTURE_2D, this.id);
		
		
		FileInputStream in = new FileInputStream(path);
        PNGDecoder decoder = new PNGDecoder(in);
        
        alpha = decoder.hasAlpha();
        int comp = alpha ? 4 : 3;
        
        ByteBuffer buf = ByteBuffer.allocateDirect(comp*decoder.getWidth()*decoder.getHeight());
        decoder.decode(buf, decoder.getWidth()*comp, alpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB);
        buf.flip();
        in.close();
        
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, alpha ? GL_RGBA : GL_RGB, decoder.getWidth(), decoder.getHeight(), 0,
        		alpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, buf);
		
		
		width = decoder.getWidth();
		height = decoder.getHeight();
	}
	
	private void create(ImageData img) throws IOException
	{
		IntBuffer ib = BufferUtils.createIntBuffer(1);
		glGenTextures(ib);
		this.id = ib.get(0);
		glBindTexture(GL_TEXTURE_2D, this.id);
		
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, img.hasAlpha() ? GL_RGBA : GL_RGB, img.getWidth(), img.getHeight(), 0,
        		img.hasAlpha() ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, img.getDataAsByteBuffer());
		
		width = img.getWidth();
		height = img.getHeight();
		alpha = img.hasAlpha();
	}
	
	public static int create(int width, int height, boolean alpha, ByteBuffer buffer)
	{
		IntBuffer ib = BufferUtils.createIntBuffer(1);
		glGenTextures(ib);
		int res= ib.get(0);
		glBindTexture(GL_TEXTURE_2D, res);
		
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, alpha ? GL_RGBA : GL_RGB, width, height, 0,
        		alpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, buffer);
        
        return res;
	}
	
	
	//getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasAlpha() {
		return alpha;
	}
}

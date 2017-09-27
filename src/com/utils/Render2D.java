package com.utils;

import static org.lwjgl.opengl.GL11.*;

import com.render.Color;
import com.render.image.Texture;

public class Render2D 
{
	private static float angle = 0.f;
	private static float zoom = 0.f;
	
	private static float px = 0.f;
	private static float py = 0.f;
	
	private static float w = 0.f;
	private static float h = 0.f;
	
	private static float zx = 0.f;
	private static float zy = 0.f;
	
	
	public static void set2DMode(int x, int y)
	{
		Render2D.w = x;
		Render2D.h = y;
		
		glViewport(0, 0, x, y);
		
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-zx, (float)w+zx, (float)h+zy, -zy, 1.f, -1.f);
        glTranslatef((float)px, (float)py, 0.f);
	}
	
	public static void setCamPos(int x, int y)
	{
		Render2D.px = x;
		Render2D.py = y;
		
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-zx, (float)w+zx, (float)h+zy, -zy, 1.f, -1.f);
        glTranslatef((float)px, (float)py, 0.f);
	}
	
	public static void setZoom(float zoom)
	{
		if(zoom < 0.f) zoom = 0.f;
		
		Render2D.zoom = zoom;
		zx = (w*zoom - w)/2;
		zy = (h*zoom - h)/2;
		
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-zx, (float)w+zx, (float)h+zy, -zy, 1.f, -1.f);
        glTranslatef((float)px, (float)py, 0.f);
	}
	
	public static void zoom(float zoom)
	{
		setZoom(Render2D.zoom + zoom);
	}
	
	public static void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void clear(Color col)
	{
		glClearColor(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	
	//shapes
	public static void renderRectangle(float x, float y, float sx, float sy, Color col1, Color col2, Color col3, Color col4)
	{
		if(col1.hasAlpha() || col2.hasAlpha() || col3.hasAlpha() || col4.hasAlpha())
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		if(angle != 0.f) rotateInternal();
		glBegin(GL_QUADS);
	        glColor4f(col1.getRed(), col1.getGreen(), col1.getBlue(), col1.getAlpha());
	        glVertex3f(x, y, 0.f);
	        
	        glColor4f(col2.getRed(), col2.getGreen(), col2.getBlue(), col2.getAlpha());
	        glVertex3f(x+sx, y, 0.f);
	        
	        glColor4f(col3.getRed(), col3.getGreen(), col3.getBlue(), col3.getAlpha());
	        glVertex3f(x+sx, y+sy, 0.f);
	        
	        glColor4f(col4.getRed(), col4.getGreen(), col4.getBlue(), col4.getAlpha());
	        glVertex3f(x, y+sy, 0.f);
        glEnd();
        if(angle != 0.f) resetTransform();
        
        if(col1.hasAlpha() || col2.hasAlpha() || col3.hasAlpha() || col4.hasAlpha())
			glDisable(GL_BLEND);
	}
	
	public static void renderRectangle(float x, float y, float sx, float sy, Color col)
	{
		renderRectangle(x, y, sx, sy, col, col, col, col);
	}
	
	
	public static void renderLine(float x, float y, float sx, float sy, float width, Color col1, Color col2)
	{
		if(col1.hasAlpha() || col2.hasAlpha())
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		glLineWidth(width);
		if(angle != 0.f) rotateInternal();
		glBegin(GL_LINES);
	        glColor4f(col1.getRed(), col1.getGreen(), col1.getBlue(), col1.getAlpha());
	        glVertex3f(x, y, 0.f);
	        
	        glColor4f(col2.getRed(), col2.getGreen(), col2.getBlue(), col2.getAlpha());
	        glVertex3f(x+sx, y+sy, 0.f);
        glEnd();
        if(angle != 0.f) resetTransform();
        
        if(col1.hasAlpha() || col2.hasAlpha())
			glDisable(GL_BLEND);
	}
	
	public static void renderLine(float x, float y, float sx, float sy, float width, Color col1)
	{
		renderLine(x, y, sx, sy, width, col1, col1);
	}
	
	public static void renderLine(float x, float y, float sx, float sy, Color col1)
	{
		renderLine(x, y, sx, sy, 1.f, col1, col1);
	}
	
	public static void renderLine(float x, float y, float sx, float sy, Color col1, Color col2)
	{
		renderLine(x, y, sx, sy, 1.f, col1, col2);
	}
	
	
	public static void renderShape(Color col, boolean alpha, float... pos)
	{
		if(alpha)
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		if(pos.length%2 != 0)
			throw new IllegalArgumentException("Too many position");
		
		if(angle != 0.f) rotateInternal();
		glBegin(GL_POLYGON);
	        for(int i=0; i<pos.length; i+=2)
	        {
	        	glColor4f(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
		        glVertex3f(pos[i], pos[i+1], 0.f);
	        }
        glEnd();
        if(angle != 0.f) resetTransform();
        
        if(alpha)
			glDisable(GL_BLEND);
	}
	
	
	public static void renderCircle(float x, float y, float radius, Color col)
	{
		if(col.hasAlpha())
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		
		final double DEG2RAD = 3.14159f/180f;
		
		glBegin(GL_POLYGON);
			for (int i=0; i < 360; i++)
			{
				double degInRad = i*DEG2RAD;
				glColor4f(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
				glVertex3d(Math.cos(degInRad)*radius + x, Math.sin(degInRad)*radius + y, 0.);
			}
        glEnd();
        
        if(col.hasAlpha())
			glDisable(GL_BLEND);
	}
	
	
	
	//imagenes
	public static void renderImage(float x, float y, float sx, float sy, Texture tex)
	{
		tex.bind();
		
		if(angle != 0.f) rotateInternal();
		
		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
	        glVertex3f(x, y, 0.f);
	        
	        glTexCoord2f(1.0f, 0.0f);
	        glVertex3f(x+sx, y, 0.f);

	        glTexCoord2f(1.0f, 1.0f);
	        glVertex3f(x+sx, y+sy, 0.f);
	        
	        glTexCoord2f(0.0f, 1.0f);
	        glVertex3f(x, y+sy, 0.f);
        glEnd();
        
        if(angle != 0.f) resetTransform();
        tex.unbind();
	}
	
	public static void renderImage(float x, float y, Texture tex)
	{
		renderImage(x, y, tex.getWidth(), tex.getHeight(), tex);
	}
	
	public static void renderImageTiled(float x, float y, float sx, float sy, int cix, int ciy, int cfx, int cfy, Texture tex)
	{
		tex.bind();
		
		float _cix = (float)cix/tex.getWidth(); 
		float _ciy = (float)ciy/tex.getHeight(); 
		float _cfx = (float)cfx/tex.getWidth(); 
		float _cfy = (float)cfy/tex.getHeight();
		
		if(angle != 0.f) rotateInternal();
		
		glBegin(GL_QUADS);
			glTexCoord2f(_cix, _ciy);
	        glVertex3f(x, y, 0.f);
	        
	        glTexCoord2f(_cfx, _ciy);
	        glVertex3f(x+sx, y, 0.f);
	        
	        glTexCoord2f(_cfx, _cfy);
	        glVertex3f(x+sx, y+sy, 0.f);
	        
	        glTexCoord2f(_cix, _cfy);
	        glVertex3f(x, y+sy, 0.f);
        glEnd();
        
        if(angle != 0.f) resetTransform();
        tex.unbind();
	}
	
	public static void renderImageTiled(float x, float y, int cix, int ciy, int cfx, int cfy, Texture tex)
	{
		renderImageTiled(x, y, cfx-cix, cfy-ciy, cix, ciy, cfx, cfy, tex);
	}
	
	public static void renderImageTiled(float x, float y, float sx, float sy, int stile, int tilex, int tiley, Texture tex)
	{
		int stilex = tex.getWidth()/stile;
		renderImageTiled(x, y, sx, sy, stilex*tilex, stilex*tiley, stilex*tilex + stilex, stilex*tiley + stilex, tex);
	}
	
	public static void renderImageTiled(float x, float y, int stile, int tilex, int tiley, Texture tex)
	{
		int stilex = tex.getWidth()/stile;
		renderImageTiled(x, y, stilex, stilex, stilex*tilex, stilex*tiley, stilex*tilex + stilex, stilex*tiley + stilex, tex);
	}
	
	
	//transform
	public static void rotate(float angle)
	{
		Render2D.angle = angle;
	}
	
	private static void rotateInternal()
	{
		glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glRotatef(angle, 0.f, 0.f, 1.f);
	}
	
	private static void resetTransform()
	{
		glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        angle = 0.f;
	}
}

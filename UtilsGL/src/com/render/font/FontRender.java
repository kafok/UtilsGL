package com.render.font;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

import com.render.image.ImageDataByteBuffer;
import com.render.image.Texture;

public class FontRender {
	
	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int CENTER = 3;
	
	private final static int LIMITS = 1024;

	//atributtes -----------------------------------------------------
	
	private int[] atlas;
	private FontRange range;
	
	public List<Glyph> glyphs;	//TODO
	
	
	//styles ---------------------------------------------------------
	
	private int charSpacing;
	private int lineSpacing;
	private int paragraphAlign;
	private int fontSize;
	
	
	//construtors ----------------------------------------------------
	
	public FontRender() {
		charSpacing = 0;
		lineSpacing = 4;
		paragraphAlign = LEFT;
		fontSize = 12;
		
		Font font = new Font("Times New Roman", Font.PLAIN, 12);
		range = new FontRange();
		range.addASCIIRange();
		
		create(font, range);
	}
	
	public FontRender(Font font) {
		charSpacing = 0;
		lineSpacing = 4;
		paragraphAlign = LEFT;
		fontSize = font.getSize();
		
		FontRange range = new FontRange();
		range.addASCIIRange();
		
		create(font, range);
	}
	
	public FontRender(Font font, FontRange range) {
		charSpacing = 0;
		lineSpacing = 4;
		paragraphAlign = LEFT;
		fontSize = font.getSize();
		this.range = range;
		
		create(font, range);
	}
	
	public FontRender(String fontName, int fontSize) {
		charSpacing = 0;
		lineSpacing = 4;
		paragraphAlign = LEFT;
		this.fontSize = fontSize;
		
		Font font = new Font(fontName, Font.PLAIN, fontSize);
		range = new FontRange();
		range.addASCIIRange();
		
		create(font, range);
	}
	
	public FontRender(String fontName, int fontSize, FontRange range) {
		charSpacing = 0;
		lineSpacing = 4;
		paragraphAlign = LEFT;
		this.fontSize = fontSize;
		this.range = range;
		
		glyphs = new LinkedList<Glyph>();
		
		Font font = new Font(fontName, Font.PLAIN, fontSize);
		
		create(font, range);
	}
	
	
	//styles ---------------------------------------------------------
	
	public int getCharSpacing() {
		return charSpacing;
	}

	public void setCharSpacing(int charSpacing) {
		this.charSpacing = charSpacing;
	}

	public int getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(int lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

	public int getParagraphAlign() {
		return paragraphAlign;
	}

	public void setParagraphAlign(int paragraphAlign) {
		this.paragraphAlign = paragraphAlign;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	//render ---------------------------------------------------------
	
	public void render(int x, int y, Character c) {
		Glyph glyph = glyphs.get(range.getPosition(c));
		
		//bind
		glBindTexture(GL_TEXTURE_2D, glyph.texture);
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	//TODO filtros
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		//render char
		glBegin(GL_QUADS);
			glTexCoord2f(glyph.sourceU, glyph.sourceV);
			glVertex3f(x, y, 0.f);
			
			glTexCoord2f(glyph.sourceU + glyph.targetU, glyph.sourceV);
			glVertex3f(x+500.f, y, 0.f);
			
			glTexCoord2f(glyph.sourceU + glyph.targetU, glyph.sourceV + glyph.targetV);
			glVertex3f(x+500.f, y+500.f, 0.f);
			
			glTexCoord2f(glyph.sourceU, glyph.sourceV + glyph.targetV);
			glVertex3f(x, y+500.f, 0.f);
		glEnd();
		
		//unbind
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	
	//create ---------------------------------------------------------
	
	private BufferedImage[] create(Font font, FontRange range) {
		List<BufferedImage> res = new LinkedList<BufferedImage>();
		int countWidth = 0;
		int countHeight = 0;
		List<String> draw = new LinkedList<String>();
		String nowString = "";
		BufferedImage nowImg = new BufferedImage(LIMITS, LIMITS, BufferedImage.TYPE_4BYTE_ABGR);
		
		Map<Character, GlyphSize> glyphSizes = new HashMap<Character, GlyphSize>();
		
		//bucle para calcular tamaños
		int charHeight = 0;
		for(Character c : range) {
			//calcular glyph
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = image.createGraphics();
			g.setFont(font);
			FontMetrics metrics = g.getFontMetrics();
			g.dispose();
			
			int charWidth = metrics.charWidth(c);
			charHeight = metrics.getAscent() + metrics.getDescent();
//			charHeight = metrics.getHeight();
			
			if (charWidth != 0) {
				//calcular texto para atlas
				if(countWidth + charWidth >= LIMITS) {
					draw.add(nowString);
					nowString = "";
					countWidth = 0;
				}
				
				nowString += c;
				countWidth += charWidth;
				
				
				if(countHeight + charHeight >= LIMITS) 
					countHeight = 0;
				else
					countHeight += charHeight;
				
				glyphSizes.put(c, new GlyphSize(charWidth, countHeight));
			}
		}
		
		
		draw.add(nowString);
		
		//preparar para dibujar
		Graphics2D g = nowImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		FontMetrics fm = g.getFontMetrics();
		countHeight = fm.getAscent();	//tamaño vertical
		countHeight = charHeight;//TODO
		countWidth = 0;
		
		//textura
		IntBuffer ib = BufferUtils.createIntBuffer(1);
		glGenTextures(ib);
		int texID = ib.get(0);
		
		//lista de glyph, donde el indice es la posicion en el FontRange
		ArrayList<Glyph> alist = new ArrayList<Glyph>();
		alist.ensureCapacity(glyphSizes.size());
		
		//bucle para pintar texturas y generar los glyph
		for(String s : draw) {
			if(countHeight + charHeight >= LIMITS) {
				//generamos textura
				glBindTexture(GL_TEXTURE_2D, texID);
		        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, LIMITS, LIMITS, 0, GL_RGBA, GL_UNSIGNED_BYTE, new ImageDataByteBuffer(nowImg).getDataAsByteBuffer());
		        
		        ib = BufferUtils.createIntBuffer(1);
				glGenTextures(ib);
				texID = ib.get(0);
				
				//actualizamos nowImg
				res.add(nowImg);
				nowImg = new BufferedImage(LIMITS, LIMITS, BufferedImage.TYPE_4BYTE_ABGR);
				
				g.dispose();
				g = nowImg.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setFont(font);
				g.setColor(Color.BLACK);
				
				countHeight = fm.getAscent();
			}
			
			countWidth = 0;
			for(int c=0; c<s.length(); c++) {
				GlyphSize gsa = glyphSizes.get(s.charAt(c));
				Glyph gn = new Glyph(texID, 1.f*countWidth/LIMITS, 1.f*(countHeight-charHeight)/LIMITS, 1.f*(gsa.w)/LIMITS, 1.f*(charHeight)/LIMITS);
				alist.add(gn);
				
//				g.drawString(""+s.charAt(c), countWidth, countHeight);
				
				countWidth += gsa.w;
			}
			
			g.drawString(s, 0, countHeight);
			countHeight += charHeight;
		}
		res.add(nowImg);
		
		//generamos ultima textura
		glBindTexture(GL_TEXTURE_2D, texID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, LIMITS, LIMITS, 0, GL_RGBA, GL_UNSIGNED_BYTE, new ImageDataByteBuffer(nowImg).getDataAsByteBuffer());
		
		g.dispose();
		
		
		//rellenamos lista
		this.glyphs = alist;
		
		//TODO optimizar memoria
//		for(Glyph g1 : alist) {
//			System.out.println(g1.sourceU + ", " + g1.sourceV + ", " +  g1.targetU + ", " +  g1.targetV);
//		}
		
		
		//to Array
		BufferedImage[] _res = new BufferedImage[res.size()];
		int i = 0;
		for(BufferedImage bi : res) {
			_res[i] = bi;
			i++;
			
		}
		
		return _res;
	}
	
	
	//glyph -----------------------------------------------------------
	
	public static class Glyph {	//TODO
		private int texture;
		private float sourceU;
		private float sourceV;
		private float targetU;
		private float targetV;
		
		public Glyph(int texture, float sourceU, float sourceV, float targetU, float targetV) {
			this.sourceU = sourceU;
			this.sourceV = sourceV;
			this.targetU = targetU;
			this.targetV = targetV;
			this.texture = texture;
		}

		public int getTexture() {
			return texture;
		}

		public float getSourceU() {
			return sourceU;
		}

		public float getSourceV() {
			return sourceV;
		}

		public float getTargetU() {
			return targetU;
		}

		public float getTargetV() {
			return targetV;
		}
	}
	
	private static class GlyphSize {
		private int w;
		private int h;
		
		public GlyphSize(int w, int h) {
			super();
			this.w = w;
			this.h = h;
		}
		
		public int getW() {
			return w;
		}
		public int getH() {
			return h;
		}
	}
}

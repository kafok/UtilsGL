package com.render;

public class ColorRGBA implements Color
{
	//atributos
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	
	//contructor
	public ColorRGBA(float red, float green, float blue, float alpha) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public ColorRGBA(float red, float green, float blue) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = 1.0f;
	}
	
	public ColorRGBA(int red, int green, int blue, int alpha) 
	{
		this.red = (float)red/255.f;
		this.green = (float)green/255.f;
		this.blue = (float)blue/255.f;
		this.alpha = (float) alpha/255.f;
	}
	
	public ColorRGBA(int red, int green, int blue) 
	{
		this.red = (float)red/255.f;
		this.green = (float)green/255.f;
		this.blue = (float)blue/255.f;
		this.alpha = 1.f;
	}
	
	public ColorRGBA(int col) 
	{
		this.red = (float)((col&0xFF000000)>>>24)/255.f;
		this.green = (float)((col&0xFF0000)>>>16)/255.f;
		this.blue = (float)((col&0xFF00)>>>8)/255.f;
		this.alpha = (float)((col&0xFF))/255.f;
	}

	
	//getter/setters
	public float getRed() {
		return red;
	}


	public void setRed(float red) {
		this.red = red;
	}


	public float getGreen() {
		return green;
	}


	public void setGreen(float green) {
		this.green = green;
	}


	public float getBlue() {
		return blue;
	}


	public void setBlue(float blue) {
		this.blue = blue;
	}


	public float getAlpha() {
		return alpha;
	}


	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	
	public long toInt() {
		return (int) (16777216*red + 65536*green + 255*blue + alpha);
	}
	
	public boolean hasAlpha() {
		return alpha < 1.0f;
	}


	//object
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(alpha);
		result = prime * result + Float.floatToIntBits(blue);
		result = prime * result + Float.floatToIntBits(green);
		result = prime * result + Float.floatToIntBits(red);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorRGBA other = (ColorRGBA) obj;
		if (Float.floatToIntBits(alpha) != Float.floatToIntBits(other.alpha))
			return false;
		if (Float.floatToIntBits(blue) != Float.floatToIntBits(other.blue))
			return false;
		if (Float.floatToIntBits(green) != Float.floatToIntBits(other.green))
			return false;
		if (Float.floatToIntBits(red) != Float.floatToIntBits(other.red))
			return false;
		return true;
	}
}

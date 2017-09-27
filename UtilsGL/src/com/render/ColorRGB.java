package com.render;

public class ColorRGB implements Color
{
	//atributos
	private float red;
	private float green;
	private float blue;
	
	
	//contructor
	public ColorRGB(float red, float green, float blue, float alpha) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public ColorRGB(float red, float green, float blue) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public ColorRGB(int red, int green, int blue) 
	{
		this.red = (float)red/255.f;
		this.green = (float)green/255.f;
		this.blue = (float)blue/255.f;
	}
	
	public ColorRGB(int col) 
	{
		this.red = (float)((col&0xFF0000)>>>16)/255.f;
		this.green = (float)((col&0xFF00)>>>8)/255.f;
		this.blue = (float)(col&0xFF)/255.f;
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
		return 1.0F;
	}


	public void setAlpha(float alpha) {
	}
	
	
	public long toInt() {
		return (int) (65536*red + 256*green + blue);
	}
	
	public boolean hasAlpha() {
		return false;
	}


	//object
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ColorRGB other = (ColorRGB) obj;
		if (Float.floatToIntBits(blue) != Float.floatToIntBits(other.blue))
			return false;
		if (Float.floatToIntBits(green) != Float.floatToIntBits(other.green))
			return false;
		if (Float.floatToIntBits(red) != Float.floatToIntBits(other.red))
			return false;
		return true;
	}
}

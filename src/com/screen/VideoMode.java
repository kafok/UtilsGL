package com.screen;

public class VideoMode 
{
	//propiedades
	private int width;
	private int height;
	private int redBits;
	private int greenBits;
	private int blueBits;
	private int refreshRate;
	
	
	//contructor
	public VideoMode(int width, int height, int redBits, int greenBits, int blueBits, int refreshRate) 
	{
		this.width = width;
		this.height = height;
		this.redBits = redBits;
		this.greenBits = greenBits;
		this.blueBits = blueBits;
		this.refreshRate = refreshRate;
	}


	//getters
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getRedBits() {
		return redBits;
	}


	public int getGreenBits() {
		return greenBits;
	}


	public int getBlueBits() {
		return blueBits;
	}


	public int getRefreshRate() {
		return refreshRate;
	}


	
	//object
	public String toString() {
		return "------------------VideoMode-----------------\n" +
				"Width: " + width + "px\n" +
				"Height: " + height + "px\n" +
				"Red Bits: " + redBits + 
				"\nGreen Bits: " + greenBits + 
				"\nBlue Bits: " + blueBits + 
				"\nRefresh Rate: " + refreshRate + "Hz\n" +
				"--------------------------------------------";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blueBits;
		result = prime * result + greenBits;
		result = prime * result + height;
		result = prime * result + redBits;
		result = prime * result + refreshRate;
		result = prime * result + width;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		VideoMode other = (VideoMode) obj;
		if (blueBits != other.blueBits)
			return false;
		if (greenBits != other.greenBits)
			return false;
		if (height != other.height)
			return false;
		if (redBits != other.redBits)
			return false;
		if (refreshRate != other.refreshRate)
			return false;
		if (width != other.width)
			return false;
		
		return true;
	}
	
	
}

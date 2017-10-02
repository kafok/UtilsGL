package com.utilsgl.render;

public interface Color {
	
	float getRed();
	void setRed(float red);
	float getGreen();
	void setGreen(float green);
	float getBlue();
	void setBlue(float blue);
	float getAlpha();
	void setAlpha(float alpha);
	
	boolean hasAlpha();
}

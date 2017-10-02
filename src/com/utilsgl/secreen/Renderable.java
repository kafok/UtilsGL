package com.utilsgl.secreen;

import java.nio.ByteBuffer;

public interface Renderable {
	
	Context getContext();
	
	boolean makeCurrentContext();
	void swapBuffers();
	void swapInterval(int i);
	
	int getWidth();
	int getHeight();
	
	ByteBuffer getAsByteBuffer();
}

package com.utilsgl.secreen;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_DEPTH_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_STEREO;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;

//TODO Doc: cuidado! es mutable y puede causar problemas con los getters de Contextable
public class Context 
{
	private int major;
	private int minor;
	private boolean compat;
	
	private boolean debug;
	private int samples;
	
	private int dephtBits;
	private int stencilBits;
	private boolean stereo;
	
	
	public Context(int major, int minor, boolean compat) 
	{
		this.major = major;
		this.minor = minor;
		this.compat = compat;
		
		debug = false;
		samples = 0;
		dephtBits = 24;
		stencilBits = 8;
		stereo = false;
	}
	
	public Context() 
	{
		this.major = 1;
		this.minor = 0;
		this.compat = false;
		
		debug = false;
		samples = 0;
		dephtBits = 24;
		stencilBits = 8;
		stereo = false;
	}

	
	//getters
	public Context setMajor(int major) {
		this.major = major;
		return this;
	}

	public Context setMinor(int minor) {
		this.minor = minor;
		return this;
	}

	public Context setCompat(boolean compat) {
		this.compat = compat;
		return this;
	}

	public Context setDebug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public Context setSamples(int samples) {
		this.samples = samples;
		return this;
	}

	public Context setDephtBits(int dephtBits) {
		this.dephtBits = dephtBits;
		return this;
	}

	public Context setStencilBits(int stencilBits) {
		this.stencilBits = stencilBits;
		return this;
	}

	public Context setStereo(boolean stereo) {
		this.stereo = stereo;
		return this;
	}

	
	//setters
	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public boolean isCompat() {
		return compat;
	}

	public boolean isDebug() {
		return debug;
	}

	public int getSamples() {
		return samples;
	}

	public int getDephtBits() {
		return dephtBits;
	}

	public int getStencilBits() {
		return stencilBits;
	}

	public boolean isStereo() {
		return stereo;
	}
	
	
	
	//crear contexto
	public void createContext()
	{
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, this.getMajor());
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, this.getMinor());
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, this.isCompat() ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, this.isDebug() ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_SAMPLES, this.getSamples());
		glfwWindowHint(GLFW_DEPTH_BITS, this.getDephtBits());
		glfwWindowHint(GLFW_STENCIL_BITS, this.getStencilBits());
		glfwWindowHint(GLFW_STEREO, this.isStereo() ? GL_TRUE : GL_FALSE);
	}
	
}

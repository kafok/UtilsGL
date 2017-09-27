package com.screen;

import static org.lwjgl.glfw.GLFW.GLFW_BLUE_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_GREEN_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_RED_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_REFRESH_RATE;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPhysicalSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;

public class Monitor {
	
	private long monitor;
	private VideoMode vm;

	private Monitor(long monitor) {
		this.monitor = monitor;

		GLFWVidMode mode = glfwGetVideoMode(monitor);
		vm = new VideoMode(mode.width(), mode.height(), mode.redBits(), mode.greenBits(), mode.blueBits(),
				mode.refreshRate());
	}

	
	// getters
	public VideoMode getVideoMode() {
		return vm;
	}

	public List<VideoMode> getVideoModes() {
		List<VideoMode> res = new LinkedList<VideoMode>();

		GLFWVidMode.Buffer bf = glfwGetVideoModes(monitor);
		for (int i = 0; i < bf.limit(); i++) {
			GLFWVidMode mode = bf.get(i);
			res.add(new VideoMode(mode.width(), mode.height(), mode.redBits(), mode.greenBits(), mode.blueBits(),
					mode.refreshRate()));
		}

		return res;
	}

	public int getPhysicalWidth() {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		glfwGetMonitorPhysicalSize(monitor, width, null);
		return width.get(0);
	}

	public int getPhysicalHeight() {
		IntBuffer height = BufferUtils.createIntBuffer(1);
		glfwGetMonitorPhysicalSize(monitor, null, height);
		return height.get(0);
	}

	public int getPosX() {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		glfwGetMonitorPos(monitor, width, null);
		return width.get(0);
	}

	public int getPosY() {
		IntBuffer height = BufferUtils.createIntBuffer(1);
		glfwGetMonitorPos(monitor, null, height);
		return height.get(0);
	}

	public String getName() {
		return glfwGetMonitorName(monitor);
	}

	public long get() {
		return monitor;
	}

	public void setVideoMode(VideoMode vm) {
		this.vm = vm;
	}

	// object
	public String toString() {
		return "Monitor: " + getName();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (monitor ^ (monitor >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Monitor other = (Monitor) obj;
		if (monitor != other.monitor)
			return false;

		return true;
	}

	// estaticos
	public static Monitor getPrimaryMonitor() {
		return new Monitor(glfwGetPrimaryMonitor());
	}

	public static List<Monitor> getMonitors() {
		List<Monitor> res = new LinkedList<Monitor>();

		PointerBuffer bf = glfwGetMonitors();
		for (int i = 0; i < bf.limit(); i++)
			res.add(new Monitor(bf.get(i)));

		return res;
	}

	// crear monitor
	public void createMonitor(VideoMode vm) {
		glfwWindowHint(GLFW_RED_BITS, vm.getRedBits());
		glfwWindowHint(GLFW_GREEN_BITS, vm.getGreenBits());
		glfwWindowHint(GLFW_BLUE_BITS, vm.getBlueBits());
		glfwWindowHint(GLFW_REFRESH_RATE, vm.getRefreshRate());
	}
}

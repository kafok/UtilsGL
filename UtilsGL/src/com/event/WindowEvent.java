package com.event;

import java.util.List;

import com.input.SpecialKey;

public class WindowEvent 
{
	public boolean onClose() { return true; }
	public void onResize(int x, int y) {}
	public void onMove(int x, int y) {}
	public void onMinimize() {}
	public void onRestore() {}
	public void onFocus() {}
	public void onLeaveFocus() {}
	public void onRender() {}
	public void onIdle() {}
	
	
	//inputs
	public void onKeyDown(int key, SpecialKey sk) {}
	public void onKeyRelease(int key, SpecialKey sk) {}
	public void onMouseMove(double x, double y) {}
	public void onHover() {}
	public void onLeave() {}
	public void onClickDown(int key) {}
	public void onClickRelease(int key) {}
	public void onMouseWheel(double offset) {}
	
	
	//drop
	public void onDrop(List<String> listPath) {}
}

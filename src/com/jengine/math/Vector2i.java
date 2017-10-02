package com.jengine.math;

public class Vector2i implements Vector<Integer>{

	private int x;
	private int y;
	
	public Vector2i(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Vector2i(Vector2i vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
	}

	public Vector2i() {
		super();
		this.x = 0;
		this.y = 0;
	}
	
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public void scale(Integer value) {
		x *= value;
		y *= value;
	}

	@Override
	public void invert() {
		x = -x;
		y = -y;
	}
}

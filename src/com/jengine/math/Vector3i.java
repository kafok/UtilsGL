package com.jengine.math;

public class Vector3i implements Vector<Integer>{

	private int x;
	private int y;
	private int z;
	
	public Vector3i(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3i vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
	}

	public Vector3i() {
		super();
		this.x = 0;
		this.y = 0;
		this.z = 0;
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
	
	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}

	@Override
	public void scale(Integer value) {
		x *= value;
		y *= value;
		z *= value;
	}

	@Override
	public void invert() {
		x = -x;
		y = -y;
		z = -z;
	}
}

package com.jengine.math;

public class Vector3f implements Vector3<Float> {

	private float x;
	private float y;
	private float z;
	

	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector3f vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
	}

	public Vector3f() {
		super();
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	
	@Override
	public double power() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	@Override
	public void normalize() {
		double norm = power();
		x /= norm;
		y /= norm;
		z /= norm;
	}

	@Override
	public void scale(Float value) {
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
	
	
	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getZ() {
		return z;
	}

	public void setZ(Float z) {
		this.z = z;
	}
	

	@Override
	public Float dot(Vector3<Float> vector) {
		return x*vector.getX() + y*vector.getY() + z*vector.getZ();
	}

	@Override
	public Vector3f cross(Vector3<Float> vector) {
		Vector3f res = new Vector3f();
		res.x = y*vector.getZ() - z*vector.getY();
		res.y = -(x*vector.getZ() - z*vector.getX());
		res.z = x*vector.getY() - y*vector.getX();
		return res;
	}

	@Override
	public double distance(Vector3<Float> vector) {
		float dx = vector.getX() - x;
		float dy = vector.getY() - y;
		float dz = vector.getZ() - z;
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}

	@Override
	public Float squareDistance(Vector3<Float> vector) {
		float dx = vector.getX() - x;
		float dy = vector.getY() - y;
		float dz = vector.getZ() - z;
		return dx*dx+dy*dy+dz*dz;
	}

	@Override
	public void sum(Vector3<Float> vector) {
		x += vector.getX();
		y += vector.getY();
		z += vector.getZ();
	}

	@Override
	public void sub(Vector3<Float> vector) {
		x -= vector.getX();
		y -= vector.getY();
		z -= vector.getZ();
	}
}

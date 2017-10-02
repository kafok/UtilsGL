package com.jengine.math;

public class Vector2f implements Vector2<Float>{

	private float x;
	private float y;

	public Vector2f(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
	}

	public Vector2f() {
		super();
		this.x = 0;
		this.y = 0;
	}

	@Override
	public double power() {
		return Math.sqrt(x*x+y*y);
	}

	@Override
	public void normalize() {
		double norm = power();
		x /= norm;
		y /= norm;
	}

	@Override
	public Float dot(Vector2<Float> vector) {
		return x*vector.getX() + y*vector.getY();
	}

	@Override
	public Vector3<Float> cross(Vector2<Float> vector) {
		Vector3f res = new Vector3f();
		res.setZ(x*vector.getY() - y*vector.getX());
		return res;
	}

	@Override
	public void scale(Float value) {
		x *= value;
		y *= value;
	}

	@Override
	public void sum(Vector2<Float> vector) {
		x += vector.getX();
		y += vector.getY();
	}

	@Override
	public void sub(Vector2<Float> vector) {
		x -= vector.getX();
		y -= vector.getY();
	}

	@Override
	public void invert() {
		x = -x;
		y = -y;
	}

	@Override
	public double distance(Vector2<Float> vector) {
		float dx = vector.getX() - x;
		float dy = vector.getY() - y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	@Override
	public Float squareDistance(Vector2<Float> vector) {
		float dx = vector.getX() - x;
		float dy = vector.getY() - y;
		return dx*dx+dy*dy;
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

}

package com.jengine.math;

public class Vector2d implements Vector2<Double>{

	private double x;
	private double y;

	public Vector2d(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Vector2d(Vector2d vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
	}

	public Vector2d() {
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
	public Double dot(Vector2<Double> vector) {
		return x*vector.getX() + y*vector.getY();
	}

	@Override
	public Vector3d cross(Vector2<Double> vector) {
		Vector3d res = new Vector3d();
		res.setZ(x*vector.getY() - y*vector.getX());
		return res;
	}

	@Override
	public void scale(Double value) {
		x *= value;
		y *= value;
	}

	@Override
	public void sum(Vector2<Double> vector) {
		x += vector.getX();
		y += vector.getY();
	}

	@Override
	public void sub(Vector2<Double> vector) {
		x -= vector.getX();
		y -= vector.getY();
	}

	@Override
	public void invert() {
		x = -x;
		y = -y;
	}

	@Override
	public double distance(Vector2<Double> vector) {
		double dx = vector.getX() - x;
		double dy = vector.getY() - y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	@Override
	public Double squareDistance(Vector2<Double> vector) {
		double dx = vector.getX() - x;
		double dy = vector.getY() - y;
		return dx*dx+dy*dy;
	}
	
	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

}

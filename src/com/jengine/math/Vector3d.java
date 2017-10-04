package com.jengine.math;

public class Vector3d implements Vector3<Double> {

	private double x;
	private double y;
	private double z;
	

	public Vector3d(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3d(Vector3d vector) {
		super();
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
	}

	public Vector3d() {
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
		if(norm == 0) {
			x = 0;
			y = 0;
			z = 0;
		} else {
			x /= norm;
			y /= norm;
			z /= norm;
		}
	}

	@Override
	public void scale(Double value) {
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

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}
	

	@Override
	public Double dot(Vector3<Double> vector) {
		return x*vector.getX() + y*vector.getY() + z*vector.getZ();
	}

	@Override
	public Vector3d cross(Vector3<Double> vector) {
		Vector3d res = new Vector3d();
		res.x = y*vector.getZ() - z*vector.getY();
		res.y = -(x*vector.getZ() - z*vector.getX());
		res.z = x*vector.getY() - y*vector.getX();
		return res;
	}

	@Override
	public double distance(Vector3<Double> vector) {
		double dx = vector.getX() - x;
		double dy = vector.getY() - y;
		double dz = vector.getZ() - z;
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}

	@Override
	public Double squareDistance(Vector3<Double> vector) {
		double dx = vector.getX() - x;
		double dy = vector.getY() - y;
		double dz = vector.getZ() - z;
		return dx*dx+dy*dy+dz*dz;
	}

	@Override
	public void sum(Vector3<Double> vector) {
		x += vector.getX();
		y += vector.getY();
		z += vector.getZ();
	}

	@Override
	public void sub(Vector3<Double> vector) {
		x -= vector.getX();
		y -= vector.getY();
		z -= vector.getZ();
	}
}

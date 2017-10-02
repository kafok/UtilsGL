package com.jengine.math;

public interface Vector3<T> extends VectorReal<T>{

	T dot(Vector3<T> vector);
	Vector3<T> cross(Vector3<T> vector);
	
	double distance(Vector3<T> vector);
	T squareDistance(Vector3<T> vector);
	
	void sum(Vector3<T> vector);
	void sub(Vector3<T> vector);
	
	T getX();
	T getY();
	T getZ();
	
	void setX(T x);
	void setY(T y);
	void setZ(T z);
	
}

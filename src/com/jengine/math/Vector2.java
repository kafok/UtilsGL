package com.jengine.math;

public interface Vector2<T> extends VectorReal<T>{

	T dot(Vector2<T> vector);
	Vector3<T> cross(Vector2<T> vector);
	
	double distance(Vector2<T> vector);
	T squareDistance(Vector2<T> vector);
	
	void sum(Vector2<T> vector);
	void sub(Vector2<T> vector);
	
	T getX();
	T getY();
	
	void setX(T x);
	void setY(T y);
	
}

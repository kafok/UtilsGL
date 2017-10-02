package com.jengine.math;

public interface VectorReal<T> extends Vector<T> {

	double power();
	void normalize();
	
	void scale(T value);
}

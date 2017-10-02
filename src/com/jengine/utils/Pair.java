package com.jengine.utils;

public class Pair<T, R> {

	private T first;
	private R second;
	
	public Pair(T first, R second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public Pair(Pair<T, R> pair) {
		super();
		this.first = pair.getFirst();
		this.second = pair.getSecond();
	}
	

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public R getSecond() {
		return second;
	}

	public void setSecond(R second) {
		this.second = second;
	}
}

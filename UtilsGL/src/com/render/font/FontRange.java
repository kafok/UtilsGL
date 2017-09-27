package com.render.font;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FontRange implements Iterable<Character> {
	
	//atributtes -----------------------------------------------------
	
	List<Range> ranges;
	
	
	//constructor ----------------------------------------------------
	
	public FontRange() {
		ranges = new LinkedList<Range>();
	}
	
	
	//range ----------------------------------------------------------
	
	public void addRange(int a, int b) {
		ranges.add(new Range(a, b));
	}
	
	public void addASCIIRange() {
		ranges.add(new Range(32, 127));
		ranges.add(new Range(128, 256));
	}
	
	
	public int getPosition(char c) {
		int code = Character.hashCode(c);
		int count = 0;
		for(Range r : ranges) {
			if(code >= r.a && code < r.b)
				return count + (code - r.a);
			else
				count += (r.b - r.a);
		}
		
		return -1;
	}
	
	//range class ----------------------------------------------------
	
	private class Range {
		public int a;
		public int b;
		
		public Range(int a, int b) {
			this.a = a;
			this.b = b;
			
			if(a < 0 || b < 0 || b <= a)
				throw new IllegalArgumentException("Invalida range");
		}
	}

	//iterable ------------------------------------------------------
	
	public Iterator<Character> iterator() {
		return new RangeIterator();
	}
	
	private class RangeIterator implements Iterator<Character> {

		private Iterator<Range> iterator;
		private Range now;
		private int num;
		
		public RangeIterator() {
			iterator = ranges.iterator();
			if(iterator.hasNext()) {
				now = iterator.next();
				num = now.a - 1;
			} else
				now = null;
		}
		
		
		public boolean hasNext() {
			return (now != null && num < (now.b - 1)) || iterator.hasNext();
		}

		public Character next() {
			num++;
			if(num == now.b) {
				now = iterator.next();
				num = now.a;
			}
			
			return Character.valueOf((char) num);
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

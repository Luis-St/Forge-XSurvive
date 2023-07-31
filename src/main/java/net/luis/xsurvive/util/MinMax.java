package net.luis.xsurvive.util;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class MinMax {
	
	private int min;
	private int max;
	
	public MinMax() {
		this(Integer.MAX_VALUE, Integer.MIN_VALUE);
	}
	
	public MinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public void update(int value) {
		this.min = Math.min(this.min, value);
		this.max = Math.max(this.max, value);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MinMax minMax)) return false;
		
		if (this.min != minMax.min) return false;
		return this.max == minMax.max;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.min, this.max);
	}
	
	@Override
	public String toString() {
		if (this.min == this.max) {
			return String.valueOf(this.min);
		} else {
			return this.min + " - " + this.max;
		}
	}
}

/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.util;

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
	public String toString() {
		if (this.min == this.max) {
			return String.valueOf(this.min);
		} else {
			return this.min + " - " + this.max;
		}
	}
}

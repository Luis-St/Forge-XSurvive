package net.luis.xsurvive.util;

/**
 *
 * @author Luis-st
 *
 */

public enum MathOperation {
	
	ADD() {
		@Override
		public int calculate(int first, int second) {
			return first + second;
		}
		
		@Override
		public long calculate(long first, long second) {
			return first + second;
		}
		
		@Override
		public float calculate(float first, float second) {
			return first + second;
		}
		
		@Override
		public double calculate(double first, double second) {
			return first + second;
		}
	},
	SUBTRACT() {
		@Override
		public int calculate(int first, int second) {
			return first - second;
		}
		
		@Override
		public long calculate(long first, long second) {
			return first - second;
		}
		
		@Override
		public float calculate(float first, float second) {
			return first - second;
		}
		
		@Override
		public double calculate(double first, double second) {
			return first - second;
		}
	},
	MULTIPLY() {
		@Override
		public int calculate(int first, int second) {
			return first * second;
		}
		
		@Override
		public long calculate(long first, long second) {
			return first * second;
		}
		
		@Override
		public float calculate(float first, float second) {
			return first * second;
		}
		
		@Override
		public double calculate(double first, double second) {
			return first * second;
		}
	},
	DIVIDE() {
		@Override
		public int calculate(int first, int second) {
			return first / second;
		}
		
		@Override
		public long calculate(long first, long second) {
			return first / second;
		}
		
		@Override
		public float calculate(float first, float second) {
			return first / second;
		}
		
		@Override
		public double calculate(double first, double second) {
			return first / second;
		}
	};
	
	public abstract int calculate(int first, int second);
	
	public abstract long calculate(long first, long second);
	
	public abstract float calculate(float first, float second);
	
	public abstract double calculate(double first, double second);
	
}

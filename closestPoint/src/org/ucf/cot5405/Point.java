package org.ucf.cot5405;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public double getDistance(Point other)
	{
		Stats.numComparisons++;
		int xDiff = x - other.getX();
		int yDiff = y- other.getY();
		return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
}

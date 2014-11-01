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
		Pair<Point, Point> compare = new Pair<Point, Point>(this, other);
		Stats.comparisons.add(compare);
		Stats.numComparisons++;
		ClosestPoint.getSingleton().updateDisplay();
		
		int xDiff = x - other.getX();
		int yDiff = y- other.getY();
		
		
		Stats.comparisons.remove(compare);
		ClosestPoint.getSingleton().updateDisplay();
		
		return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
}

package org.ucf.cot5405.algorithm;

import java.util.List;

import org.ucf.cot5405.Pair;
import org.ucf.cot5405.Point;
import org.ucf.cot5405.Stats;

public class NaiveAlgorithm implements ClosestPointAlgo{

	@Override
	public Pair<Point, Point>  getClosestPoint(List<Point> points) {
		Stats.reset();
		double minimumDistance = Double.MAX_VALUE;
		Pair<Point, Point> smallest = null;
		
		for(Point p1 : points)
		{
			for(Point p2 : points)
			{
				if(p1 == p2)
				{
					continue;
				}
				double dist = p1.getDistance(p2);
				if(dist < minimumDistance)
				{
					minimumDistance = dist;
					smallest = new Pair<Point, Point>(p1,p2);
				}
			}
		}
		return smallest;
	}
	
}

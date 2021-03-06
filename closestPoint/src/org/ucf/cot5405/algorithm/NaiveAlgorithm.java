package org.ucf.cot5405.algorithm;

import java.util.List;

import org.ucf.cot5405.ClosestPoint;
import org.ucf.cot5405.Pair;
import org.ucf.cot5405.Point;
import org.ucf.cot5405.Stats;

public class NaiveAlgorithm implements ClosestPointAlgo{

	@Override
	public Pair<Point, Point>  getClosestPoint(List<Point> points) {
		Stats.reset();
		ClosestPoint.getSingleton().updateDisplay();
		double minimumDistance = Double.MAX_VALUE;
		Pair<Point, Point> smallest = null;
		
		//for(Point p1 : points)
		for(int i=0; i<points.size(); i++)
		{
			Point p1 = points.get(i);
			for(int j=0; j<i; j++)
			//for(Point p2 : points)
			{
				Point p2 = points.get(j);
//				if(p1 == p2)
//				{
//					continue;
//				}
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

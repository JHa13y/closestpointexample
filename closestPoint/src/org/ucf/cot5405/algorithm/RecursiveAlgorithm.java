package org.ucf.cot5405.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ucf.cot5405.ClosestPoint;
import org.ucf.cot5405.Pair;
import org.ucf.cot5405.Point;
import org.ucf.cot5405.Stats;

public class RecursiveAlgorithm implements ClosestPointAlgo{

	@Override
	public Pair<Point, Point>  getClosestPoint(List<Point> points) {
		Stats.reset();
		ClosestPoint.getSingleton().updateDisplay();
		Collections.sort(points, new Comparator<Point>()
		{

			@Override
			public int compare(Point p1, Point p2) {
				Stats.numComparisons++;
				if(p1.getX() < p2.getX())
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
			
		});
		return internalGetClosest(points);
	}
	
	private Pair<Point, Point> internalGetClosest(List<Point> points)
	{
		Pair<Point, Point> smallest = null;
		
		if(points.size() == 2)
		{
			return new Pair<Point, Point>(points.get(0), points.get(1));
		}
		else if(points.size() == 1 || points.size() == 0)
		{
			return null;
		}
		
		int n = points.size()/2;
		
		
		List<Point> left = points.subList(0, n);
		List<Point> right = points.subList(n, points.size());
		
		Double cut = (left.get(left.size()-1).getX()+ right.get(0).getX()) /2.0;
		Stats.cuts.add(cut);
		ClosestPoint.getSingleton().updateDisplay();
		
		
		Pair<Point,Point> leftSmallest = internalGetClosest(left);
		double leftDistance =Double.MAX_VALUE;
		if(leftSmallest != null)
		{
			Stats.shortests.add(leftSmallest);
			ClosestPoint.getSingleton().updateDisplay();
			leftDistance = leftSmallest.getLeft().getDistance(leftSmallest.getRight());
		}
		
		
		Pair<Point,Point> rightSmallest = internalGetClosest(right);
		double rightDistance =Double.MAX_VALUE;
		if(rightSmallest != null)
		{
			Stats.shortests.add(rightSmallest);
			ClosestPoint.getSingleton().updateDisplay();
			rightDistance = rightSmallest.getLeft().getDistance(rightSmallest.getRight());
		}
		if(rightDistance < leftDistance)
		{
			smallest = rightSmallest;
		}
		else
		{
			smallest = leftSmallest;
		}
		Double minDel = Math.min(leftDistance, rightDistance);
//		minDel = Math.min(minDel, cut - left.get(left.size()-1).getX());
//		minDel = Math.min(minDel, right.get(right.size()-1).getX() - cut);
		Pair<Integer, Double> delta = new Pair<Integer, Double>(cut.intValue(), minDel);
		Stats.deltas.add(delta);
		
		ClosestPoint.getSingleton().updateDisplay();
		
		//Get remaining Points
		List<Point> remaining = new ArrayList<Point>();
		for(Point p : points)
		{
			if(Math.abs(p.getX() - cut) < delta.getRight())
			{
				remaining.add(p);
			}
		}
		
		//Sort by Y
		Collections.sort(remaining, new Comparator<Point>()
		{

			@Override
			public int compare(Point p1, Point p2) {
				Stats.numComparisons++;
				if(p1.getY() < p2.getY())
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
			
		});
		
		//TODO: Perform Delta Search!!
		double minimumDist = delta.getRight();
		Pair<Point, Point> deltaSmallest;
		for(Point p : remaining)
		{
			int index = remaining.indexOf(p);
			for(int i=index; i < Math.min(index + 11, remaining.size()-1); i++)
			{
				Point p2 = remaining.get(i);
				if(p == p2)
				{
					continue;
				}
				double dist = p.getDistance(p2);
				if(dist < minimumDist)
				{
					minimumDist = dist;
					deltaSmallest = new Pair<Point, Point>(p,p2);
					smallest = deltaSmallest;
				}
			}
		}
		
		if(leftSmallest != null)
		{
			Stats.shortests.remove(leftSmallest);
		}
		
		if(rightSmallest != null)
		{
			Stats.shortests.remove(rightSmallest);
		}
		Stats.cuts.remove(cut);
		Stats.deltas.remove(delta);
		ClosestPoint.getSingleton().updateDisplay();
		return smallest;
	}
}

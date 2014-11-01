package org.ucf.cot5405.algorithm;

import java.util.List;

import org.ucf.cot5405.Pair;
import org.ucf.cot5405.Point;

public interface ClosestPointAlgo {
	public Pair<Point, Point> getClosestPoint(List<Point> points);
}

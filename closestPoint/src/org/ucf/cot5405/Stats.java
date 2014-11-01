package org.ucf.cot5405;

import java.util.ArrayList;
import java.util.List;

public class Stats{
	static public int numComparisons =0;
	static public List<Double> cuts;
	static public List<Pair<Point, Point>> comparisons;
	
	//X = cut, Y = deltas
	static public List<Pair<Integer, Double>> deltas;
	
	static public void reset()
	{
		numComparisons =0;
		List<Double> cuts = new ArrayList<Double>();
		List<Pair<Integer, Double>> deltas = new ArrayList<Pair<Integer, Double>>();
		List<Pair<Point, Point>> comparisons = new ArrayList<Pair<Point, Point>>();
	}
}

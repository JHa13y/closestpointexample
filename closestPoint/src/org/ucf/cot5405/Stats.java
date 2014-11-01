package org.ucf.cot5405;

import java.util.ArrayList;
import java.util.List;

public class Stats{
	static public int numComparisons =0;
	static public List<Double> cuts;
	static public List<Pair<Point, Point>> comparisons;
	static public List<Pair<Point, Point>> shortests;
	
	//X = cut, Y = deltas
	static public List<Pair<Integer, Double>> deltas;
	
	static public void reset()
	{
		numComparisons =0;
		cuts = new ArrayList<Double>();
		deltas = new ArrayList<Pair<Integer, Double>>();
		comparisons = new ArrayList<Pair<Point, Point>>();
		shortests = new ArrayList<Pair<Point, Point>>();
	}
}

package org.ucf.cot5405;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.ucf.cot5405.algorithm.NaiveAlgorithm;


public class ClosestPoint extends JFrame {
	private static ClosestPoint instance = new ClosestPoint();
	int n = 25;
	int delay = 25;
	List<Point> points;
	Pair<Point, Point> closestPair = null;
	PointCanvas canvas = new PointCanvas();
	
	public static ClosestPoint getSingleton()
	{
		return instance;
	}
	
	public ClosestPoint()
	{
		super("Closest Point");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
		//...create emptyLabel...
		//TODO
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

		mainPane.add(canvas);
		this.setSize(canvas.getWidth(), canvas.getHeight() + 50);
		JPanel Controls = new JPanel(new GridLayout());
		final TextField nEntry = new TextField(4);
		nEntry.setText(Integer.toString(n));
		final int oldN = n;
		nEntry.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String newN = nEntry.getText();
				try
				{
					int n = Integer.parseInt(newN);
					setN(n);
				}
				catch(Exception ex)
				{
					 String infoMessage = "Error Please enter an Integer Value of n!";
					 JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
					 nEntry.setText(Integer.toString(oldN));
				}
				
			}
			
		});
		
		final TextField delayEntry = new TextField(this.delay);
		final int delay = this.delay;
		delayEntry.setText(Integer.toString(delay));
		delayEntry.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String newN = delayEntry.getText();
				try
				{
					int n = Integer.parseInt(newN);
					setDelay(n);
				}
				catch(Exception ex)
				{
					 String infoMessage = "Error Please enter an Integer Value for delay";
					 JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
					 delayEntry.setText(Integer.toString(delay));
				}
				
			}
			
		});
		
		Button genPoints = new Button("Generate Points");
		genPoints.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				runGeneratePoints();
			}
			
		});
		
		Button runNaive = new Button("Run Naive Search");
		runNaive.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				runNaiveSearch();
			}
			
		});
		
		Button runDivide = new Button("Run Recursive Search");
		runDivide.setEnabled(false); //TODO: Remove when Implemented

		Controls.add(new Label("Number of Points:"));
		Controls.add(nEntry);
		Controls.add(genPoints);
		Controls.add(new Label("Set Drawing Delay:"));
		Controls.add(delayEntry);
		Controls.add(runNaive);
		Controls.add(runDivide);
		
		mainPane.add(Controls);//, BorderLayout.SOUTH);
		
		java.awt.Container contentPane = this.getContentPane();
		contentPane.add(mainPane, BorderLayout.CENTER);
		//4. Size the frame.
		this.pack();

		//5. Show it.
		canvas.setVisible(true);
		this.setVisible(true);
		updateDisplay();
	}
	
	private void setN(int n)
	{
		this.n = n;
	}
	
	private void setDelay(int delay)
	{
		this.delay = delay;
	}
	
	public void runGeneratePoints()
	{
		Random rand = new Random(System.currentTimeMillis());
		int size = 750;
		this.points = new ArrayList<Point>();
		
		for(int i=0; i <n; i++)
		{
			int x = rand.nextInt(750);
			int y = rand.nextInt(750);
			points.add(new Point(x,y));
		}
		closestPair = null;
		updateDisplay();
	}
	
	public void runNaiveSearch()
	{
		closestPair = (new NaiveAlgorithm()).getClosestPoint(this.points);
		updateDisplay();
		String infoMessage = "Closest Points Found! " + Stats.numComparisons + " Comparisons Executed!";
		JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void updateDisplay()
	{
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {}
		
		canvas.paintComponent(canvas.getGraphics());
	}

	
	
	public static void main(String[] args)
	{
		ClosestPoint p = ClosestPoint.getSingleton();
	}
	
	private class PointCanvas extends JPanel
	{
		private Font monoFont = new Font("Monospaced", Font.BOLD
			      | Font.ITALIC, 12);
		
		PointCanvas()
		{
			Dimension dim = new Dimension(750, 750);
			this.setSize(dim);
			this.setPreferredSize(dim);
			this.setMaximumSize(dim);
			this.setMinimumSize(dim);
			
			this.setBorder(new LineBorder(Color.BLACK));
		}
		

		@Override
        public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
            Graphics g = graphics.create();
			g.setColor(Color.black);

		    g.setFont(monoFont);
		    FontMetrics fm = g.getFontMetrics();
		    int w = fm.stringWidth("Test");
		    int h = fm.getAscent();
		    g.drawString("Test", 120 - (w / 2), 120 + (h / 4));
		    g.dispose();

		}
		private void drawDeltas(Graphics g)
		{
			
		}
		private void drawGrid(Graphics g)
		{
			
		}
		
		private void drawPoints(Graphics g){
			
		}
		
		private void drawComparisons(Graphics g)
		{
			
		}
	}
}

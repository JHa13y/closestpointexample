package org.ucf.cot5405;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
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
import org.ucf.cot5405.algorithm.RecursiveAlgorithm;


public class ClosestPoint extends JFrame {
	private static ClosestPoint instance = new ClosestPoint();
	int n = 25;
	int delay = 50;
	List<Point> points;
	Pair<Point, Point> closestPair = null;
	PointCanvas canvas = new PointCanvas();
	boolean hasUpdate = false;
	
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
					 return;
				}
				
				runGeneratePoints();
			}
			
		});
		
		Button runNaive = new Button("Run Naive Search");
		runNaive.addActionListener(new ActionListener(){

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
					 return;
				}
				runNaiveSearch();
			}
			
		});
		
		Button runDivide = new Button("Run Recursive Search");
		runDivide.addActionListener(new ActionListener(){

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
					 return;
				}
				runDivideSearch();
			}
			
		});

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
		if(points == null)
		{
			String infoMessage = "Error:  Generate Points before search";
			JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		closestPair = (new NaiveAlgorithm()).getClosestPoint(this.points);
		updateDisplay();
		String infoMessage = "Closest Points Found! " + Stats.numComparisons + " Comparisons Executed!";
		JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void runDivideSearch()
	{
		if(points == null)
		{
			String infoMessage = "Error:  Generate Points before search";
			JOptionPane.showMessageDialog(null, infoMessage, "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		closestPair = (new RecursiveAlgorithm()).getClosestPoint(this.points);
		updateDisplay();
		String infoMessage = "Closest Points Found! " + Stats.numComparisons + " Comparisons Executed!";
		JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void updateDisplay()
	{
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {}
		hasUpdate = true;
		canvas.paint(getGraphics());
		//canvas.update(canvas.getGraphics());
	}

	
	
	public static void main(String[] args)
	{
		ClosestPoint p = ClosestPoint.getSingleton();
	}
	
	private class PointCanvas extends Canvas
	{
		private Font monoFont = new Font("Monospaced", Font.BOLD
			      | Font.ITALIC, 6);
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		
		PointCanvas()
		{
			Dimension dim = new Dimension(750, 750);
			this.setSize(dim);
			this.setPreferredSize(dim);
			this.setMaximumSize(dim);
			this.setMinimumSize(dim);
			
			//this.setBorder(new LineBorder(Color.BLACK));
		}
		

		@Override
        public void paint(Graphics graphics) {
			if(!hasUpdate)
				return;
			//super.update(graphics);
           /// Graphics g = graphics.create();
			if(this.getBufferStrategy() == null)
			{
				this.createBufferStrategy(3);
			}
			BufferStrategy strategy = this.getBufferStrategy();
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setFont(monoFont);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
			drawDeltas(g);
			drawGrid(g);
			drawPoints(g);
			drawClosests(g);
			drawComparisons(g);
			drawSolution(g);
		    g.dispose();
		    
		    strategy.show();
		    Toolkit.getDefaultToolkit().sync();
		}
		
		private void drawDeltas(Graphics g)
		{
			
			if(Stats.comparisons != null)
			{
				
				for(Pair<Integer, Double> deltas : Stats.deltas)
				{
					g.setColor(Color.GRAY);
					int leftX = (int) (deltas.getLeft() - deltas.getRight());
					int width = (int) (deltas.getRight() * 2);
					
					g.fillRect(leftX, 0, width, canvas.getHeight());
					
				}
				for(Double cut : Stats.cuts)
				{
					g.setColor(Color.black);
					Graphics2D g2d = (Graphics2D)g;
					Stroke original = g2d.getStroke();
			        g2d.setStroke(dashed);
					g.drawLine(cut.intValue(), 0, cut.intValue(), canvas.getHeight());
					g2d.setStroke(original);
				}
				
			}
		}
		private void drawGrid(Graphics g)
		{
			g.setColor(Color.black);
			//Draw X Axis Ticks
			for(int i=0; i < 750 ; i += 25)
			{
				g.drawLine(i, 0, i, 10);
				g.drawString(Integer.toString(i), i-5, this.getHeight() - 10);
				g.drawLine(i, this.getHeight(), i, this.getHeight() - 5);
			}
			for(int i=0; i < 750 ; i += 25)
			{
				g.drawLine(0, i, 5, i);
				g.drawString(Integer.toString(i), 5, i+2);
				g.drawLine(this.getWidth(), i, this.getWidth() - 10, i);
			}
		}
		
		private void drawPoints(Graphics g){
			g.setColor(Color.black);
			if(points != null)
			{
				for(Point p : points)
				{
					g.drawOval(p.getX(), p.getY(), 3, 3);
				}
			}
			
		}
		
		private void drawComparisons(Graphics g)
		{
			g.setColor(Color.RED);
			if(Stats.comparisons != null)
			{
				for(Pair<Point, Point> comp : Stats.comparisons)
				{
					Point p1 = comp.getLeft();
					Point p2 = comp.getRight();
					g.drawOval(p1.getX(), p1.getY(), 3, 3);
					g.drawOval(p2.getX(), p2.getY(), 3, 3);
					g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				}
			}
		}
		private void drawClosests(Graphics g)
		{
			g.setColor(Color.GREEN);
			if(Stats.shortests != null)
			{
				for(Pair<Point, Point> shortest: Stats.shortests)
				{
					Point p1 = shortest.getLeft();
					Point p2 = shortest.getRight();
					g.drawOval(p1.getX(), p1.getY(), 3, 3);
					g.drawOval(p2.getX(), p2.getY(), 3, 3);
					g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				}
			}
		}
		
		private void drawSolution(Graphics g)
		{
			g.setColor(Color.CYAN);
			if(closestPair != null)
			{
				Point p1 = closestPair.getLeft();
				Point p2 = closestPair.getRight();
				g.drawOval(p1.getX(), p1.getY(), 3, 3);
				g.drawOval(p2.getX(), p2.getY(), 3, 3);
				g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
			}
		}
	}
}

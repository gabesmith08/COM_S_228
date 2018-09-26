package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richard Smith
 *
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Scanner;



/**
 * 
 * This class implements construction of the convex hull of a finite number of points. 
 *
 */

public abstract class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------
	protected String algorithm;  // Its value is either "Graham's scan" or "Jarvis' march". 
	                             // Initialized by a subclass.
	
	protected long time;         // execution time in nanoseconds
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	private Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan

	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
		if(pts.length == 0)
		{
			throw new IllegalArgumentException();
		}
		points = pts;
		quicksorter = new QuickSortPoints(pts);
		removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
	}
	
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) described for the previous constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{		
		ArrayList<Integer> xList = new ArrayList<Integer>(); // holds all scanned X values
		ArrayList<Integer> yList = new ArrayList<Integer>(); // holds all scanned Y values
		int count = 0; // counts if number of integers in file is odd or even to throw InputMismathException
		
		try
		{
			File file = new File(inputFileName); // grab given file
			Scanner sc = new Scanner(file); // initiate scanner to given file
			
			while(sc.hasNextLine()) // while scanner has another row to read
			{
				while(sc.hasNext()) // while scanner still has a number to read in current row
				{
					count++; // increment count by one
					sc.next(); // scan next item from file
					if(count % 2 != 0) // if count is odd
					{
						xList.add(sc.nextInt()); // it means it's an x value
					}
					else // if not odd then
					{
						yList.add(sc.nextInt()); // add to yList
					}	
				}
			}
			sc.close(); // close scanner
			
			if(count % 2 != 0) // if the input of integers was odd
			{
				throw new InputMismatchException(); // throw new input mismatch exception
			}
			for(int i = 0; i < count; i++) // for loop to fill points array with gathered x and y values
			{
				points[i] = new Point(xList.get(i), yList.get(i));
			}
		}
		catch(FileNotFoundException e) // end of try catch to catch a file not found
		{
			e.printStackTrace();
		}
		quicksorter = new QuickSortPoints(points);
		removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
	}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * Graham's scan   1000	  9200867
	 *  
	 * Use the spacing in the sample run in Section 5 of the project description. 
	 */
	public String stats()
	{	//TODO 
		
		String result = algorithm + "   " + pointsNoDuplicate.length + "     ";
//		
//		long startTime = System.nanoTime();
//	
//		long endTime   = System.nanoTime();
//		long totalTime = endTime - startTime;
//		System.out.println(totalTime);
//		
		return result; 
	}
	
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE. 
	 *  
	 * Called only after constructHull().  
	 */
	public String toString()
	{
		int count = 0; // keeps track of 5 points per row
		String result = "";
		
		for(int i = 0; i < hullVertices.length; i++) // for loop to iterate through hullVertices[]
		{	
			count++; // increment count by 1
			result += "(" + hullVertices[i].getX() + ", " + hullVertices[i].getY() + ")" + " " + " " + " "; // add points to result string
			
			if(count == 5) // if 5 points have been added to result string, new line and reset count
			{
				count = 0;
				result += "\n";
			}
		}
		return result; 
	}
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 */
	public void writeHullToFile() throws IllegalStateException 
	{	
		if(hullVertices.length == 0)
		{
			throw new IllegalStateException();
		}
		PrintWriter pw;
		try 
		{
			pw = new PrintWriter("hull.txt");
			for(int i = 0; i < hullVertices.length; i++)
			{
				pw.println (hullVertices[i].getX() + " " + hullVertices[i].getY());
			}
			pw.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{ 
		int numSegs = hullVertices.length;  // number of segments to draw 

		// Based on Section 4, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		for(int i = 0; i < hullVertices.length -1; i++) // length -1 to get last segment correct
		{
			segments[i] = new Segment(hullVertices[i], hullVertices[i +1]);
		}
		segments[segments.length - 1] = new Segment(hullVertices[hullVertices.length -1], hullVertices[0]); // add last segment

		
		// The following statement creates a window to display the convex hull.
		Plot.myFrame(pointsNoDuplicate, segments, getClass().getName());
		
	}

		
	/**
	 * Sort the array points[] by y-coordinate in non-decreasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates()
	{
		QuickSortPoints qs = new QuickSortPoints(points); // create quicksorter
		NewComparator<Point> comp = new NewComparator<Point>(); // create comparator object
		ArrayList<Point> noDups = new ArrayList<Point>(); // made temp list to hold points
		qs.quickSort(comp);
		
		for (int i = 0; i < points.length - 1; i++) 
	    {
	            if(!points[i].equals(points[i + 1])) // if 2 points have a matching Y value
	            {
	            	noDups.add(points[i]); // fill up noDups arraylist with current iteration point
	            }
	    }	
		noDups.add(points[points.length - 1]);
		pointsNoDuplicate = new Point[noDups.size()];
		for (int i = 0; i < (noDups.size()); i++) // for loop to fill up pointsNoDuplicate[] with noDups array
	    {
			this.pointsNoDuplicate[i] = noDups.get(i);
	    }	

	}

	
}

package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richard Smith
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;


public class JarvisMarch extends ConvexHull
{
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the rightmost one in case of a tie)
	private Point highestPoint; 
	
	// left chain of the convex hull counterclockwise from lowestPoint to highestPoint
	private PureStack<Point> leftChain; 
	
	// right chain of the convex hull counterclockwise from highestPoint to lowestPoint
	private PureStack<Point> rightChain; 
		

	/**
	 * Call corresponding constructor of the super class.  Initialize the variable algorithm 
	 * (from the class ConvexHull). Set highestPoint. Initialize the two stacks leftChain 
	 * and rightChain. 
	 * 
	 * @throws IllegalArgumentException  when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		algorithm = "Jarvis' march";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length -1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
	}

	
	/**
	 * Call corresponding constructor of the superclass.  Initialize the variable algorithm.
	 * Set highestPoint.  Initialize leftChain and rightChain.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		algorithm = "Jarvis' march";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length -1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
	}


	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain().  Merge the two chains stored on the stacks  
	 * rightChain and leftChain into the array hullVertices[].
	 * 
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points from them.   
	 */
	public void constructHull()
	{	// TODO ask on degenerate cases
		
		createRightChain();
		createLeftChain();
		Point[] hullVertices = new Point[leftChain.size() + rightChain.size()]; // set size of hullVertices
		
			for(int i = hullVertices.length -1; i >= 0; i--) // reverse for loop to fill hullVertices array
			{
				if(leftChain.isEmpty() != true) // keep popping leftChain until empty, then pop rightChain
				{
					hullVertices[i] = leftChain.pop(); // fill up hullVertices[] with leftChain 
				}
				else
				{
					hullVertices[i] = rightChain.pop(); // fill up hullVertices[] with rightChain
				}
			}
	}
	
	
	/**
	 * Construct the right chain of the convex hull.  Starts at lowestPoint and wrap around the 
	 * points counterclockwise.  For every new vertex v of the convex hull, call nextVertex()
	 * to determine the next vertex, which has the smallest polar angle with respect to v.  Stop 
	 * when the highest point is reached.  
	 * 
	 * Use the stack rightChain to carry out the operation.  
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createRightChain()
	{
		rightChain.push(lowestPoint); // push lowest point to start rightChain
		while(!rightChain.peek().equals(highestPoint)) // keep going until we hit the highest point is reached
		{
			rightChain.push(nextVertex(rightChain.peek())); // push the next vertex onto the left chain
		}
	}
	
	
	/**
	 * Construct the left chain of the convex hull.  Starts at highestPoint and continues the 
	 * counterclockwise wrapping.  Stop when lowestPoint is reached.  
	 * 
	 * Use the stack leftChain to carry out the operation. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createLeftChain()
	{
		leftChain.push(highestPoint); // push highest point to start leftChain
		while(!leftChain.peek().equals(lowestPoint)) // keep going until we hit the lowest point
		{
			leftChain.push(nextVertex(leftChain.peek())); // push the next vertex onto the left chain
		}
	}
	
	
	/**
	 * Return the next vertex, which is less than all other points by polar angle with respect
	 * to the current vertex v. When there is a tie, pick the point furthest from v. Comparison 
	 * is done using a PolarAngleComparator object created by the constructor call 
	 * PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param v  current vertex 
	 * @return
	 */
	public Point nextVertex(Point v)
	{	// TODO double check if correct
		PolarAngleComparator comp = new PolarAngleComparator(v, false); // create new comparator off of given point
		Point temp = new Point(pointsNoDuplicate[0]); // create temp point to hold the next vertex
		
		for(int i = 1; i < pointsNoDuplicate.length; i++)
		{
			if (comp.compare(temp, pointsNoDuplicate[i]) == 1) // if comparing temp current iteration on pointsNoDuplicate[] = 1
			{
				temp = pointsNoDuplicate[i]; // temp = current iteration of pointsNoDuplicate[]
			}
		}
		return temp;
	}
}

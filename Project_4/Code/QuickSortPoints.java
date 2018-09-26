package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richard Smith
 *
 */


import java.util.Comparator;

/**
 * This class sorts an array of Point objects using a provided Comparator.  For the purpose
 * you may adapt your implementation of quicksort from Project 2.  
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.
	

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		points = pts;
	}
	
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{	
		for(int i = 0; i < pts.length; i++) // deep copy
		{
			points[i] = new Point(pts[i].getX(), pts[i].getY());
		}
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 * 
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		int first = 0; // variable to hold first index in points array
		int last = points.length -1; // variable to hold last index in points array
		quickSortRec(first, last, comp); // call quickSortRec() to start quicksort recursion
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		if (first < last)
		{
			int pivot = partition(first, last, comp);
			quickSortRec(first, pivot -1, comp); // changes pivot index's for recursion
			quickSortRec(pivot +1, last, comp); 
		}
	}
	

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{	
		Point pivot = points[last]; // grabs element farthest to the right
		int pivIndex = first - 1; // index for pivot
		
		for(int j = first; j < last; j++)
		{
			if(comp.compare(points[j], pivot) < 0) // compares the current point with pivot point
			{
				pivIndex++;
				Point temp = new Point(points[pivIndex]);
				points[pivIndex] = new Point(points[j]);
				points[j] = temp;
			}
		}
		Point temp = new Point(points[pivIndex +1]);
		points[pivIndex +1] = new Point(points[last]);
		points[last] = temp;
		
		return pivIndex +1;
	}
}



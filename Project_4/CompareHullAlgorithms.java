package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richard Smith
 *
 */

/**
 * 
 * This class executes two convex hull algorithms: Graham's scan and Jarvis' march, over randomly
 * generated integers as well integers from a file input. It compares the execution times of 
 * these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) 
	{		
		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		
		Scanner sc = new Scanner(System.in); // Scanner for user input
		int pointAmt = 0;
		int trialNum = 0; // holds how many trials have been made
		int trialOption = 1; // holds option the user makes(1 or 2) 
		String fileName = ""; // holds user input for text file option
		ConvexHull[] algorithms = new ConvexHull[2]; // holds both scans
		long timeAfter, timeBefore;
		
		while(trialOption <= 2) // clicking zero exits the 
		{	
			trialNum++; // increment trial number for every full trial iteration
			System.out.println("Comparison between Convex Hull Algorithms");
			System.out.println("Trial " + trialNum + ": ");
			trialOption = sc.nextInt();
			
			if(trialOption == 0) // exit program
			{
				System.exit(0);
			}
			if(trialOption == 1) // if random points is chosen
			{
				Random rand = new Random();
				System.out.println("Enter number of random points: ");
				pointAmt = sc.nextInt();
				Point[] pts = new Point[pointAmt]; // create point array to store random points into
				pts = generateRandomPoints(pointAmt, rand);
				ConvexHull graham = new GrahamScan(pts);
				ConvexHull jarvis = new JarvisMarch(pts);
				algorithms[0] = graham;
				algorithms[1] = jarvis;
			}
            //	2) If the input is from a file, construct two objects of the classes GrahamScan and  
			//  JarvisMarch, respectively, using the file. 
			if(trialOption == 2) // 
			{
				System.out.println("Points from a file\nFile name: ");
				fileName = sc.nextLine();
				try {
					ConvexHull graham = new GrahamScan(fileName);
					ConvexHull jarvis = new JarvisMarch(fileName);
					algorithms[0] = graham;
					algorithms[1] = jarvis;
				} catch (InputMismatchException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			sc.close();
			//	3) Have each object call constructHull() to build the convex hull of the input points.
			//	4) Meanwhile, prints out the table of runtime statistics.
			System.out.println("algorithm         size         time(ns)");
			
			timeBefore = System.nanoTime();
			algorithms[0].constructHull();
			timeAfter = System.nanoTime();
			algorithms[0].time = timeAfter - timeBefore;
			System.out.println("---------------------------------------"); 
			System.out.println(algorithms[0].stats() + algorithms[0].time);
			
			timeBefore = System.nanoTime();
			algorithms[1].constructHull();
			timeAfter = System.nanoTime();
			algorithms[1].time = timeAfter - timeBefore;
			System.out.println(algorithms[1].stats() + algorithms[1].time);
			System.out.println("---------------------------------------");
		
			for(int i = 0; i < 2; i++)
			{
				algorithms[i].draw();
			}
		// 
		// A sample scenario is given in Section 5 of the project description. 
		// 	
		
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 5). 
		}
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] × [-50,50]. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 	//TODO ask on random number ranges
		
		Point[] pts = new Point[numPts]; // new pts array to be filled up and returned
		if(numPts < 1)
		{
			throw new IllegalArgumentException();
		}
		for(int i = 0; i < numPts; i++)
		{
			pts[i] = new Point(rand.nextInt(101)-50, rand.nextInt(101)-50); // fill pts[] with new point from random number generator
		}
		return pts;
	}
}

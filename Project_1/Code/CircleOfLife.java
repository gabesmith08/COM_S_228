package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 * 
 * The CircleOfLife class performs simulation over a grid jungle 
 * with squares occupied by deers, jaguars, pumas, grass, or none. 
 *
 */
public class CircleOfLife 
{
	/**
	 * Update the new jungle from the old jungle in one cycle. 
	 * @param jOld  old jungle
	 * @param jNew  new jungle 
	 */
	public static void updateJungle(Jungle jOld, Jungle jNew)
	{ 
		for(int i = 0; i < jOld.getWidth(); i++)
		{
			for(int j = 0; j < jOld.getWidth(); j++)  // 2 for loops that iterates old grid length
			{
				jNew.grid[i][j] = jOld.grid[i][j].next(jNew);  // old grid is filled with new grid every iteration
			}
		}
	}
	
	/**
	 * Repeatedly generates jungles either randomly or from reading files. 
	 * Over each jungle, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	// double spaced this method to make the comments look neater
		
		Jungle even = new Jungle(0);  // stores initial jungle and even jungle 
		
		Jungle odd = new Jungle(0);  // stores odd jungle
		
		Scanner scan = new Scanner(System.in);  // scanner to take input from keyboard
		
		boolean running = true;  // boolean variable for while loop, if anything other than 1 "random jungle" or 2 "file input"
								 // is entered by user for the key variable the program will skip if statements and change boolean to false.
								 // resulting in closing scanner and exiting the program.
		
		int key = 0;  // holds user input to decide route to take. "random jungle", "file input", or "exit program" 
		
		int width = 0;  // holds width for both jungles
		
		int numCycles = 0;  // holds number of cycles the user input
		
		int trialCt = 1;  // keeps count of how many trials you run
		
		
		System.out.print("Circle of Life in the Amazon Jungle\n"
						 + "Keys: 1 (random jungle)  2 (file input)  3 (exit)\n");

		while(running == true)  // initially runs through while loop no matter what choice of input the user chose
		{
			System.out.print("\nTrial: " + trialCt + " ");
			
			key = scan.nextInt();  // sets key to user input choice
			
			if(key == 1)  // if random jungle was chosen
			{ 
				System.out.print("\nRandom Jungle\n");
				System.out.print("Enter grid width: ");
				
				width = scan.nextInt();  // scans number user enters for width of grid and stores in width variable
				
				even = new Jungle(width);  // set even jungle to user given width
				
				odd = new Jungle(width);  // set odd jungle to user given width
				
				even.randomInit();  // can set even or odd as initial jungle but i chose even. doesn't matter
				
				System.out.print("\nEnter the number of cycles: ");
				
				numCycles = scan.nextInt();  // sets numCycles variable with input from the user
				
				System.out.print("\n\nInitial Jungle:\n\n");
				
				System.out.print(even);

				for(int i = 0; i < numCycles; i++)  // loop until number of cycles is reached
				{ 
					if((i % 2) != 0)  // if current cycle is odd
					{
						updateJungle(odd, even);  // udates odd jungle
					}
					else
					{
						updateJungle(even, odd);  // updates even jungle
					}
				}

				System.out.print("\n\nFinal Jungle:\n\n");
				
				if((numCycles % 2) == 0)  // if number of cycles was even
				{ 
					System.out.print(even);
				}
				else
				{
					System.out.print(odd);
				}
			}
			else if (key == 2)
			{
				System.out.print("\nJungle input from a file\n");
				System.out.print("File name: ");
				
				String file = scan.next();  // sets file string to scanned file grid
				
				even = new Jungle(file);  // creates new jungle for even cycles
				
				odd = new Jungle(file);  // created new jungle for odd cycles
				
				System.out.print("\nEnter the number of cycles: ");
				
				numCycles = scan.nextInt();  // sets number of cycles to input the user chose
				
				System.out.print("\n\nInitial Jungle:\n\n");
				
				System.out.print(even);  // used even jungle as initial, doesn't matter if you choose odd or even cause they're the same

				for(int i = 0; i < numCycles; i++)  // loop until number of cycles is reached
				{ 
					if((i % 2) != 0)  // if current cycle is odd
					{
						updateJungle(odd, even);  // updates odd jungle
						
					}
					else
					{
						updateJungle(even, odd);  // updates even jungle
					}
				}

				System.out.print("\n\nFinal Jungle:\n\n");
				
				if((numCycles % 2) == 0)  // if number of cycles was even
				{ 
					System.out.print(even); 
				}
				else
				{
					System.out.print(odd); 
				}
			}
			else
			{
				running = false;  // terminates and exits program if neither 1 or 2 was inputed for key variable
			}
			trialCt++;  // increments count every time user goes through full a trial
		}
		scan.close();  // close scanner
	}
}

package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The jungle is represented as a square grid of size width X width. 
 *
 */
public class Jungle 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid;
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Jungle(String inputFileName) throws FileNotFoundException
	{	
        File file = new File(inputFileName);  // gets file for scanner to scan
        Scanner scanW = new Scanner(file);  // scanner to find width of grid
        Scanner scan = new Scanner(file);  // scanner to find life forms
        int age;  // variable to hold age in switch statement
        
        while(scanW.hasNextLine())  // while scanner has another line to read in the grid file
        {
        	String empty = scanW.nextLine();  // scan life forms in grid, each separated by white space, might be useless variable though.
        	if(!empty.isEmpty())  // if line is empty don't increase width
        		width++;  // increment width variable by 1
        }
        grid = new Living[width][width]; // constructs new blank grid based off found width
        
        for(int i = 0; i < width; i++)
        {
        	for(int j = 0; j < width; j++)  // 2 for loops to scan through the file and fill grid points
        	{
        		String lf = scan.next();  // string variable to hold what life form is in the scanner at the current iteration
        		
        		switch(lf.substring(0, 1)) // compares first letter of each scanned string to the case statements
        		{
        			case("G"):  // if G is scanned, create Grass in grid spot
        				grid[i][j] = new Grass(this, i, j);
        				break;
        			case("E"):  // if E is scanned, create an Empty in grid spot
        				grid[i][j] = new Empty(this, i, j);
        				break;
        			case("J"):  // if J is scanned, create Jaguar in grid spot
        				age = Integer.parseInt(lf.substring(1, 2)); // used Integer.parseInt to convert number in string to int for age of Jaguar
        				grid[i][j] = new Jaguar(this, i, j, age);
        				break;
        			case("P"):  // if P is scanned, create Puma in grid spot
        				age = Integer.parseInt(lf.substring(1, 2)); // used Integer.parseInt to convert number in string to int for age of Puma
    					grid[i][j] = new Puma(this, i, j, age);
        				break;
        			case("D"):  // if D is scanned, create Deer in grid spot
        				age = Integer.parseInt(lf.substring(1, 2)); // used Integer.parseInt to convert number in string to int for age of Deer
    					grid[i][j] = new Deer(this, i, j, age);
        				break;
        		}
        	}
        }
        scanW.close();
        scan.close();  // close both scanners
	}
	
	/**
	 * Constructor that builds a w X w grid without initializing it. 
	 * @param width  the grid 
	 */
	public Jungle(int w)
	{
		grid = new Living[w][w];  // builds new grid from given width
		width = w;  // sets width
	}
	
	
	public int getWidth()
	{  
		return width;  // returns width of current grid
	}
	
	/**
	 * Initialize the jungle by randomly assigning to every square of the grid  
	 * one of Deer, Empty, Grass, Jaguar, or Puma.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		int ran;  // variable to hold whatever random lifeform is produced
		Random generator = new Random();  // random variable
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < width; j++)  // 2 for loops to iterate through the grid and fill it up with random life forms
			{
				ran = generator.nextInt(5);  // fills ran variable with random number in range 0 to 4 every loop iteration
				
				switch(ran)  // used another switch because i think it looks neater than if statements sometimes.
				{
					case 0:  // creates new Deer in grid spot if number is 0
						grid[i][j] = new Deer(this, i, j, 0);
						break;
					case 1:  // creates new Empty in grid spot if number is 1
						grid[i][j] = new Empty(this, i, j);
						break;
					case 2:  // creates new Grass in grid spot if number is 2
						grid[i][j] = new Grass(this, i, j);
						break;
					case 3:  // creates new Jaguar in grid spot if number is 3
						grid[i][j] = new Jaguar(this, i, j, 0);
						break;
					case 4:  // creates new Puma in grid spot if number is 4
						grid[i][j] = new Puma(this, i, j, 0);
						break;
				}
			}
		}
	}
	
	
	/**
	 * Output the jungle grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String output = "";  // string variable to hold the jungle grid in string form
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < width; j++)  // double for loop to 
			{
				if(grid[i][j].who() == State.EMPTY)  // if name of life form matches Empty, add "E " with a space to the output string variable
				{
					output += ("E  ");
				}
				else if(grid[i][j].who() == State.GRASS)  // if name of life form matches Grass, add "G " with a space to the output string variable
				{
					output += ("G  ");
				}
				else if(grid[i][j].who() == State.DEER)  // if name of life form matches Deer, add "D" + "age " with a space to the output string variable
				{
					output += ("D" + ((Animal) grid[i][j]).myAge() + " ");
				}
				else if(grid[i][j].who() == State.JAGUAR)  // if name of life form matches Jaguar, add "J" + "age " with a space to the output string variable
				{
					output += ("J" + ((Animal) grid[i][j]).myAge() + " ");
				}
				else if(grid[i][j].who() == State.PUMA)  // if name of life form matches Puma, add "P" + "age " with a space to the output string variable
				{
					output += ("P" + ((Animal) grid[i][j]).myAge() + " ");
				}
			}
			output = output + "\n";  // puts in new line to create the rows of the 2d array grid
		}
		return output;  // return grid that's converted to string
	}
	

	/**
	 * Write the jungle grid to an output file.  Also useful for saving a randomly 
	 * generated jungle for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		String output = "";  // variable for print writer to write to
		File file = new File(outputFileName);  // gets file and stores into file variable
		PrintWriter pw = new PrintWriter(file);  // Opens the file for print writer to write off of
		output += this.toString(); // stores the grid in string form
		pw.print(output);  // prints grid string to given file name
		pw.close();  // Closes print writer
	}			
}

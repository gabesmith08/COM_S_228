package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/** 
 * 
 * Living refers to the life form occupying a square in a jungle grid. It is a
 * superclass of Empty, Grass, and Animal, the last of which is in turn a
 * superclass of Deer, Jaguar, and Puma. Living has two abstract methods
 * awaiting implementation.
 *
 */
public abstract class Living 
{
	
	protected Jungle jungle;  // the jungle in which the life form resides
	
	protected int row;  // location of the square on which
	
	protected int column;  // the life form resides

	// constants to be used as indices.
	protected static final int DEER = 0;
	protected static final int EMPTY = 1;
	protected static final int GRASS = 2;
	protected static final int JAGUAR = 3;
	protected static final int PUMA = 4;

	public static final int NUM_LIFE_FORMS = 5;

	// life expectancies
	public static final int DEER_MAX_AGE = 6;
	public static final int JAGUAR_MAX_AGE = 5;
	public static final int PUMA_MAX_AGE = 4;

	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a jungle.
	 * 
	 * @param population
	 *            counts of all life forms
	 */
	protected void census(int population[]) 
	{ 	// variables to determine boundaries of the neighborhood
		int left = 0;
		int top = 0;
		int right = 2;
		int bottom = 2;
		
		if(column -1 < 0)  // if one step left of the grid point is off the grid 
		{	
			left++;  // increment the left variable by one to fix boundary
		}
		
		if(row -1 < 0)  // if one step above the grid point is off the grid
		{	
			top++;  // increment the top variable by one to fix boundary
		}
		
		if(column +1 > jungle.grid[0].length -1)  // if one step right of the grid point is off the grid 
		{	
			right--;  // subtract the right variable by one to fix boundary
		}
		
		if(row +1 > jungle.grid.length -1)  // if one step below the grid point is off the grid 
		{	
			bottom--;  // subtract the bottom variable by one to fix boundary
		}
		for (int i = top; i <= bottom; i++)   // iterates through neighborhood grid to find total population count of all Deer's, Empty's, Grass's,
		{										// Jaguar's, and Puma's in the neighborhood with fixed boundaries.
			for (int j = left; j <= right; j++) 
			{	
				if (jungle.grid[row + i - 1][column + j -1].who() == State.DEER)   // if iteration of grid is a DEER
				{						
					population[DEER]++;  // increment DEER population count by 1	
				}
				else if (jungle.grid[row + i - 1][column + j -1].who() == State.EMPTY)  // if current point in grid is EMPTY
				{	
					population[EMPTY]++;  // increment EMPTY population count by 1
				}
				else if (jungle.grid[row + i - 1][column + j -1].who() == State.GRASS)  // if current point in grid is GRASS
				{	
					population[GRASS]++;  // increment GRASS population count by 1
				}
				else if (jungle.grid[row + i - 1][column + j -1].who() == State.JAGUAR)  // if current point in grid is a JAGUAR
				{	
					population[JAGUAR]++;  // increment JAGUAR population count by 1
				}
				else if (jungle.grid[row + i - 1][column + j -1].who() == State.PUMA)  // if current point in grid is a PUMA
				{	
					population[PUMA]++;  // increment PUMA population count by 1
				}
			}
		}
	}

	/**
	 * Gets the identity of the life form on the square.
	 * 
	 * @return State
	 */
	public abstract State who();
	// To be implemented in each class of Deer, Empty, Grass, Jaguar, and Puma.
	//
	// There are five states given in State.java. Include the prefix State in
	// the return value, e.g., return State.Puma instead of Puma.

	/**
	 * Determines the life form on the square in the next cycle.
	 * 
	 * @param jNew
	 *            jungle of the next cycle
	 * @return Living
	 */
	public abstract Living next(Jungle jNew);
	// To be implemented in the classes Deer, Empty, Grass, Jaguar, and Puma.
	//
	// For each class (life form), carry out the following:
	//
	// 1. Obtains counts of life forms in the 3X3 neighborhood of the class
	// object.

	// 2. Applies the survival rules for the life form to determine the life
	// form
	// (on the same square) in the next cycle. These rules are given in the
	// project description.
	//
	// 3. Generate this new life form at the same location in the jungle jNew.

}

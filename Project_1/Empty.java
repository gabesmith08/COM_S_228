package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{
	public Empty (Jungle j, int r, int c) 
	{
		jungle = j;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Deer, Grass, Jaguar, or Puma, or 
	 * remain empty. 
	 * @param jNew     jungle in the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Jungle jNew)
	{
		int[] lifeFormsPop = new int[NUM_LIFE_FORMS];  // array that holds population of life forms in neighborhood
		
		census(lifeFormsPop);  // call census method to get neighborhood population
		
		if(lifeFormsPop[DEER] > 1)  // If there is more than 1 Deer in the neighborhood, return Deer.
		{	
			return new Deer(jNew, row, column, 0);
		}	
		else if(lifeFormsPop[PUMA] > 1)  // If there is more than 1 Puma in the neighborhood, return Puma.
		{	
			return new Puma(jNew, row, column, 0);
		}	
		else if(lifeFormsPop[JAGUAR] > 1)  // If there is more than 1 Jaguar in the neighborhood, return Jaguar.
		{	
			return new Jaguar(jNew, row, column, 0); 
		}	
		else if(lifeFormsPop[GRASS] >= 1)  // If there is at least 1 Grass in the neighborhood, return Grass.
		{	
			return new Grass(jNew, row, column);
		}	
		else
		{	
			return new Empty(jNew, row, column);  // else, remain Empty
		}
	}
}

package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/**
 * Grass may be eaten out or taken over by deers. 
 */
public class Grass extends Living 
{
	public Grass (Jungle j, int r, int c) 
	{
		jungle = j;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many deers in the neighborhood. Deers may also 
	 * multiply fast enough to take over Grass. 
	 * 
	 * @param jNew     jungle in the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Jungle jNew)
	{	
		int[] lifeFormsPop = new int[NUM_LIFE_FORMS];  // array that holds population of life forms in neighborhood
		
		census(lifeFormsPop);  // call census method to get neighborhood population
		
		if(lifeFormsPop[DEER] >= (lifeFormsPop[GRASS]*3))  // If there are at least 3 times as many deer than grasses in the neighborhood, return empty.
		{	
			return new Empty(jNew, row, column);
		}
		else if(4 <= lifeFormsPop[DEER])  // If there are at least 4 deer in the neighborhood, return deer.
		{	
			return new Deer(jNew, row, column, 0);  
		}	
		else  // else, grass stays
		{	
			return new Grass(jNew, row, column);
		}
	}
}

package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/**
 * A deer eats grass and lives no more than six years.
 */
public class Deer extends Animal 
{	
	/**
	 * Creates a Deer object.
	 * @param j: jungle  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Deer (Jungle j, int r, int c, int a) 
	{
		jungle = j;
		row = r;
		column = c;
		age = a;
	}
		
	/**
	 *  Deer occupies the square.
	 */
	public State who()
	{
		return State.DEER; 
	}
	
	/**
	 * @param jNew     jungle in the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Jungle jNew)
	{	
		int[] lifeFormsPop = new int[NUM_LIFE_FORMS];  // array that holds population of life forms in neighborhood
		
		census(lifeFormsPop);  // call census method to get neighborhood population
		
		if(DEER_MAX_AGE <= age)  // if Deer dies from old age it returns empty
		{	
			return new Empty(jNew, row, column);
		}
		else if(lifeFormsPop[GRASS] == 0)  // if Deer dies from no nearby food it returns empty
		{	
			return new Empty(jNew, row, column);
		}
		// if more Pumas and Jaguars together than Deers and if there is twice the amount of Pumas than Jaguars. Puma takes spot
		else if((lifeFormsPop[PUMA] + lifeFormsPop[JAGUAR]) > lifeFormsPop[DEER])
		{	
			if (lifeFormsPop[PUMA] >= lifeFormsPop[JAGUAR]*2)
				return new Puma(jNew, row, column, 0);
			else if(lifeFormsPop[PUMA] <= lifeFormsPop[JAGUAR])
				return new Jaguar(jNew, row, column, 0);
			else 
				return new Deer(jNew, row, column, age +1); 
			
		}	
		else  // else, deer lives on
		{	
			return new Deer(jNew, row, column, age +1);  
		}
	}
}

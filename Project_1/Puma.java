package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/**
 * A puma eats deers and competes against a jaguar. 
 */
public class Puma extends Animal 
{
	/**
	 * Constructor 
	 * @param j: jungle
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Puma (Jungle j, int r, int c, int a) 
	{
		jungle = j;
		row = r;
		column = c;
		age = a;
	}
		
	/**
	 * A puma occupies the square. 	 
	 */
	public State who()
	{
		return State.PUMA; 
	}
	
	/**
	 * A puma dies of old age or hunger, or from attack by a jaguar. 
	 * @param jNew     jungle in the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Jungle jNew)
	{	
		int[] lifeFormsPop = new int[NUM_LIFE_FORMS];  // array that holds population of life forms in neighborhood
		
		census(lifeFormsPop);  // call census method to get neighborhood population
		
		if(PUMA_MAX_AGE <= age)  // if Puma dies from old age it returns empty
		{	
			return new Empty(jNew, row, column);
		}
		else if(lifeFormsPop[PUMA] < lifeFormsPop[JAGUAR])  // if less pumas than jaguars, jaguar takes spot
		{	
			return new Jaguar(jNew, row, column, 0); 
		}	
		else if((lifeFormsPop[JAGUAR] + lifeFormsPop[PUMA]) > lifeFormsPop[DEER])  // if Jaguars and Pumas together out number Deers in the neighborhood, returns empty
		{	
			return new Empty(jNew, row, column);
		}
		else  // else, puma lives on
		{	
			return new Puma(jNew, row, column, age +1);
		}
	}
}

package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/**
 * A jaguar eats a deer and competes against a puma. 
 */
public class Jaguar extends Animal
{
	/**
	 * Constructor 
	 * @param j: jungle
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Jaguar (Jungle j, int r, int c, int a) 
	{
		jungle = j;
		row = r;
		column = c;
		age = a;
	}
	
	/**
	 * A jaguar occupies the square. 	 
	 */
	public State who()
	{
		return State.JAGUAR; 
	}
	
	/**
	 * A jaguar dies of old age or hunger, from isolation and attack by more numerous pumas.
	 *  
	 * @param jNew     jungle in the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Jungle jNew)
	{	
		int[] lifeFormsPop = new int[NUM_LIFE_FORMS];  // array that holds population of life forms in neighborhood
		
		census(lifeFormsPop);  // call census method to get neighborhood population
		
		if(JAGUAR_MAX_AGE <= age)  // Jaguar dies of old age and returns empty
		{	
			return new Empty(jNew, row, column);
		}
		else if(lifeFormsPop[JAGUAR]*2 <= lifeFormsPop[PUMA])  // if there are at least twice as many Pumas as Jaguars in the neighborhood, returns Puma 
		{	
			return new Puma(jNew, row, column, 0); 
		}	
		else if((lifeFormsPop[JAGUAR] + lifeFormsPop[PUMA]) > lifeFormsPop[DEER])  // if Jaguars and Pumas together outnumber Deers in the neighborhood, returns empty
		{	
			return new Empty(jNew, row, column);
		}
		else  // else, Jaguar lives on
		{	
			return new Jaguar(jNew, row, column, age +1);
		}
	}
}

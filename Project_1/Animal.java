package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

/**
 * This class is to be extended by the Deer, Jaguar, and Puma classes. 
 */
public abstract class Animal extends Living implements MyAge
{
	protected int age;  // age of the animal 

	@Override
	/**
	 * 
	 * @return age of the animal 
	 */
	public int myAge()
	{
		return age; 
	}
}

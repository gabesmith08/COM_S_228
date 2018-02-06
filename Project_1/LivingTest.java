package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */
import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

public class LivingTest {

	

	@Test
	public void testCensus() throws FileNotFoundException {
		
		int[] population = new int[5];
		int[] compare = {2, 1, 2, 3, 1};  // correct population count for "1-3x3.txt" jungle grid to compare to population array that census filled
		
		Jungle j = new Jungle("1-3x3.txt");
	
		j.grid[1][1].census(population);
		assertArrayEquals(population, compare);  // if true, census correctly filled population array for grid
	}
}
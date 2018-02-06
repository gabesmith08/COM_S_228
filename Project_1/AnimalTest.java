package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileNotFoundException;

public class AnimalTest {

	@Test
	public void testMyAge() throws FileNotFoundException {
		
		
		Jungle j = new Jungle("1-3x3.txt");
		
		int age = ((Animal)j.grid[1][0]).myAge();  // down casts to get age
		
		assertEquals(0, age);
		
	}

}

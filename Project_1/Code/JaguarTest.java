package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class JaguarTest {

	@Test
	public void test() throws FileNotFoundException {  // checks if jaguar works correctly through one updated jungle cycle.
		
		Jungle j = new Jungle("1-3x3.txt");
		Jungle jNew = new Jungle(3);
		
		CircleOfLife.updateJungle(j, jNew);
		assertEquals(jNew.grid[2][0].who(), State.JAGUAR);
		
		
	}

}

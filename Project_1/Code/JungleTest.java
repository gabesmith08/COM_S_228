package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;

public class JungleTest {

	@Test
	public void randomInItTest() throws FileNotFoundException {
		
		Jungle j = new Jungle(3);
		
		j.randomInit();
		
		assertTrue(j.grid[1][0] != null);  // chose random spot on grid to see if it was filled or not
	}

}

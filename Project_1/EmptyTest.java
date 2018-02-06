package edu.iastate.cs228.hw1;

/**
 * @author Richard Smith
 */

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class EmptyTest {

	@Test
	public void test() throws FileNotFoundException { // checks if Empty updates correctly through one jungle update cycle
	
		Jungle j = new Jungle("1-3x3.txt");
		Jungle jNew = new Jungle(3);
		
		CircleOfLife.updateJungle(j, jNew);
		assertEquals(jNew.grid[2][1].who(), State.EMPTY);
		
	}

}

package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * An implementation of {@link Sorter} that performs insertion sort to sort the
 * list.
 *
 * @author Richard Smith
 */
public class InsertionSorter extends Sorter {
	@Override
	public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException {

			    for (int i = 1; i < toSort.length(); i++) 
			    {
			      int j = i;
			      
			      while (j > 0 && (comp.compare(toSort.get(j), toSort.get(j - 1)) < 0 )) // while j is > 0 and compare method returns a negative number, keep swapping
			      {
			    	String temp = toSort.get(j);
			        toSort.set(j, toSort.get(j-1) );
			        toSort.set(j-1, temp);
			        j--;
			      }
			    }
	}
}

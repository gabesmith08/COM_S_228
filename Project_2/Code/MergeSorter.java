package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * An implementation of {@link Sorter} that performs merge sort to sort the
 * list.
 *
 * @author Richard Smith 
 * 
 * didn't get to test any of my sorts and this one in particular may very well be incorrect. Sorry:(
 */
public class MergeSorter extends Sorter {
	@Override
	public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException {
		
		if(toSort == null || comp == null)
		{
			throw new NullPointerException();
		}
		mergeSortRec(toSort, comp, 0, toSort.length() - 1);
	}

	private void mergeSortRec(WordList list, Comparator<String> comp, int start, int end) {
		
		int mid = (end - start) / 2;
		int n0 = mid - start + 1;
		int n1 = end - mid;
		String[] words = list.getArray();
		String[] left = new String[mid - start + 1];
		String[] right = new String[end - mid];
		
		for(int i = 0; i < n0; i++)
		{
			left[i] = words[i + 1];
		}
		for(int i = 0; i < n1; i++)
		{
			right[i] = words[i + mid + 1];
		}
		
		// now merge the temporary arrays back into the "words" array
		
		int ind0 = 0;
		int ind1 = 0;
		int ind2 = start;
		
		while(ind0 < n0 && ind1 < n1)
		{
			if(comp.compare(left[ind0], right[ind1]) <= 0)
			{
				words[ind2] = left[ind0];
				ind1++;
			}
			else
			{
				words[ind2] = right[ind1];
				ind1++;
			}
			ind2++;
		}
		
		while(ind0 < n0) // copies remaining elements of the left array
		{
			words[ind2] = left[ind0];
			ind0++;
			ind2++;
		}
		
		while(ind1 < n1) // copies remaining elements of the right array
		{
			words[ind2] = right[ind1];
			ind1++;
			ind2++;
		}
	}
}

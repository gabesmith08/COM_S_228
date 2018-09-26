package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * An implementation of {@link Sorter} that performs quick sort to sort the
 * list.
 *
 * @author Richard Smith
 */
public class QuickSorter extends Sorter {
	@Override
	public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException {

			int start = 0;
			int end = toSort.length() -1;
			quickSortRec(toSort, comp, start, end);
		
	}

	private void quickSortRec(WordList list, Comparator<String> comp, int start, int end) {

		if (start >= end) // base case
			return;
		int pivot = partition(list, comp, start, end);
		
		quickSortRec(list, comp, start, pivot -1); // changes pivot index's for recursion
		quickSortRec(list, comp, pivot +1, end);
	}

	private int partition(WordList list, Comparator<String> comp, int start, int end) {
		
		String pivot = list.get(end); // grabs element farthest to the right
		int pivIndex = start; // index for pivot
		
		for(int i = start; i < end; i++)
		{
			if(comp.compare(list.get(i), pivot) < 0) // compares the current string with pivot string
			{
				list.swap(i, pivIndex); // swaps current with whats at the pivot's index 
			}
		}
		list.swap(end, pivIndex);
		
		return pivIndex;
	}
}

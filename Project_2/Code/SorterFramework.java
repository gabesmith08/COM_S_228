package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.Comparator;

/**
 * An class that compares various methods of sorting.
 *
 * @author Richard Smith
 * 
 * Sorry for not commenting most things and implementing everything to the fullest. I had 4 exams these
 * past two weeks and it was very time consuming. But that's college! Should have managed my time better.
 */
public class SorterFramework {
	/**
	 * Loads data necessary to run the sorter statistics output, and runs it.
	 * The only logic within this method should be that necessary to use the
	 * given file names to create the {@link AlphabetComparator},
	 * {@link WordList}, and sorters to use, and then using them to run the
	 * sorter statistics output.
	 *
	 * @param args
	 *            an array expected to contain two arguments: - the name of a
	 *            file containing the ordering to use to compare characters -
	 *            the name of a file containing words containing only characters
	 *            in the other file
	 * @throws FileNotFoundException 
	 * @throws NullPointerException 
	 * @throws CloneNotSupportedException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws NullPointerException, FileNotFoundException, IllegalArgumentException, CloneNotSupportedException {
		
		Alphabet alphabet = new Alphabet (args[0]);
		AlphabetComparator comparator = new AlphabetComparator(alphabet); // calls constructor for alphabet comparator 
		WordList words;
		Sorter[] sorters = new Sorter[3]; // set up array with the different sorters
		sorters[0] = new QuickSorter();
		sorters[1] = new InsertionSorter();
		sorters[2] = new MergeSorter();
		words = new WordList(args[1]);
		int tot = 1000000;
		SorterFramework toRun = new SorterFramework(sorters, comparator, words, tot);
		toRun.run();
	}

	/**
	 * The comparator to use for sorting.
	 */
	private Comparator<String> comparator;

	/**
	 * The words to sort.
	 */
	private WordList words;

	/**
	 * The array of sorters to use for sorting.
	 */
	private Sorter[] sorters;

	/**
	 * The total amount of words expected to be sorted by each sorter.
	 */
	private int totalToSort;

	/**
	 * Constructs and initializes the SorterFramework.
	 *
	 * @param sorters
	 *            the array of sorters to use for sorting
	 * @param comparator
	 *            the comparator to use for sorting
	 * @param words
	 *            the words to sort
	 * @param totalToSort
	 *            the total amount of words expected to be sorted by each sorter
	 * @throws NullPointerException
	 *             if any of {@code sorters}, {@code comparator}, {@code words},
	 *             or elements of {@code sorters} are {@code null}
	 * @throws IllegalArgumentException
	 *             if {@code totalToSort} is negative
	 */
	public SorterFramework(Sorter[] sorters, Comparator<String> comparator, WordList words, int totalToSort)
			throws NullPointerException, IllegalArgumentException {
			
			this.comparator = comparator;
			this.totalToSort = totalToSort;
			this.sorters = sorters;
			this.words = words;
	}

	/**
	 * Runs all sorters using
	 * {@link Sorter#sortWithStatistics(WordList, Comparator, int)
	 * sortWithStatistics()}, and then outputs the following information for
	 * each sorter: - the name of the sorter - the length of the word list
	 * sorted each time - the total number of words sorted - the total time used
	 * to sort words - the average time to sort the word list - the number of
	 * elements sorted per second - the total number of comparisons performed
	 * @throws CloneNotSupportedException 
	 * @throws IllegalArgumentException 
	 * @throws NullPointerException 
	 */
	public void run() throws NullPointerException, IllegalArgumentException, CloneNotSupportedException {
		for(int i = 0; i < sorters.length; i++)
		{
			sorters[i].sortWithStatistics(words, comparator, totalToSort);
		}
	}
}

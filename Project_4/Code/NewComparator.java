package edu.iastate.cs228.hw4;

import java.util.Comparator;

public class NewComparator <T extends Comparable<T>> implements Comparator<T>
{
	/**
	 * used for quicksorter
	 */
	@Override
	public int compare(T p1, T p2)
	{
		return p1.compareTo(p2);
	}
}

package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * A string comparator that uses an ordering of an {@link Alphabet} to determine
 * how to compare individual characters.
 *
 * @author Richard Smith
 */
public class AlphabetComparator implements Comparator<String> {
	/**
	 * The ordering used to compare characters.
	 */
	private Alphabet alphabet;

	/**
	 * Constructs and initializes the comparator to use the given ordering.
	 *
	 * @param ordering
	 *            the ordering to use to compare characters
	 * @throws NullPointerException
	 *             if {@code ordering} is {@code null}
	 */
	public AlphabetComparator(Alphabet ordering) throws NullPointerException {
		alphabet = ordering;
	}

	/**
	 * Compares the two given strings based on the ordering used by this
	 * comparator.
	 *
	 * Returns a negative value if the first string is considered less than the
	 * second, a positive value if greater than the second, and zero if the two
	 * strings are equal. Note that an exception must be thrown if the strings
	 * contain invalid characters, even if the two strings are equal.
	 *
	 * For each character of the given strings, the ordering is consulted to
	 * determine which of the two characters should go first, with the one with
	 * a lesser position in the ordering being determined to be lesser. If the
	 * position of the characters are the same, the next character is examined.
	 * After the end of one of the strings is reached, the shorter string is
	 * considered to be lesser than the other.
	 *
	 * @throws NullPointerException
	 *             if either of {@code a} or {@code b} are {@code null}
	 * @throws IllegalArgumentException
	 *             if either of {@code a} or {@code b} contain a character not
	 *             found in this comparator's ordering
	 */
	@Override
	public int compare(String a, String b) throws NullPointerException, IllegalArgumentException {
		int length = b.length(); // holds smallest length of both strings so I don't get an out of bounds exception with my for loop

		if (a.length() < b.length()) // default length will be String b's length if b is less than a or a and b are ==
		{
			length = a.length();
		}
		for (int i = 0; i < length; i++) 
		{
			if (a.charAt(i) != b.charAt(i)) // if character at same index in both strings aren't equal
			{
				if (alphabet.isValid(a.charAt(i)) == true && alphabet.isValid(b.charAt(i)) == true) // if both characters are valid in the alphabet
				{
					return alphabet.getPosition(a.charAt(i)) - alphabet.getPosition(b.charAt(i)); // return negative or positive
				}
			}
		}
		if (a.length() != b.length()) 
		{
			if (length == a.length()) 
			{
				return -1;
			} else
				return 1;
		}
		return 0;
	}
}

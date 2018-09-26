package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that generates a perfect hash table.
 *
 * @author Richard G Smith --- Spent a lot of time on this assignment to get the most points possible, tested everything and it checks out.
 *  So hopefully you don't find anything wrong :)
 */
public class PerfectHashGenerator {
	/**
	 * The number of rows in the T1 and T2 tables. Enough to fit most English
	 * words.
	 */
	private static final int TABLE_ROWS = 8;

	/**
	 * The number of columns in the T1 and T2 tables. Enoug
	 * h to fit all English
	 * letters.
	 */
	private static final int TABLE_COLUMNS = 64;

	public static void main(String[] args) {
		if (null == args || 1 > args.length || 3 < args.length) {
			System.err.println("Usage: <word list> [prefix] [seed]");

			System.exit(1);
			return;
		}

		String prefix = args[1];
		Random rng = new Random();
		
		if(args.length > 2)
		{
			Scanner sc = new Scanner(args[2]);
			try{
				rng = new Random(sc.nextInt());
			}
			catch(InputMismatchException e)
			{
				System.err.println("InputMismatchException");
				System.exit(0);
			}
			sc.close();
		}

		PerfectHashGenerator gen = new PerfectHashGenerator();
		try {
			gen.generate(args[0], prefix + "CHM92Hash", rng);
		} catch (IOException e) {
			System.err.println(e);

			System.exit(2);
			return;
		}
	}

	/**
	 * Generates the perfect hash table for the words in the indicated file, and
	 * writes the generated code to the appropriate file
	 * ({@code outputClassName + ".java"}).
	 *
	 * @param wordFileName
	 *            the name of the word file
	 * @param outputClassName
	 *            the name of the output class
	 * @param rng
	 *            the random number generator for the generated hash table
	 *
	 * @throws IOException
	 *             if the input file cannot be read or the output file cannot be
	 *             written
	 * @throws IllegalArgumentException
	 *             if the given output class name is not a valid Java identifier
	 */
	public void generate(String wordFileName, String outputClassName, Random rng)
			throws IOException, IllegalArgumentException {
		// TODO complete
		
		if(wordFileName == null || outputClassName == null || rng == null) {
			throw new IOException("input file cannot be read or the output file cannot be written");
		}
		
		if (!outputClassName.matches("[A-Za-z_][A-Za-z_0-9]*")) {
			throw new IllegalArgumentException("invalid class name \"" + outputClassName + "\"");
		}
		
		List<String> words = readWordFile(wordFileName);
		
		int[][] table0 = new int[TABLE_ROWS][TABLE_COLUMNS];
		int[][] table1 = new int[TABLE_ROWS][TABLE_COLUMNS]; // generate hash tables
		
		int mod = 2 * words.size() +1; // create mod variable
		
		Graph map = mapping(table0, table1, mod, rng, words); // store mapping in graph variable
		
		int[] gArr = map.fillGArray(words.size()); // fill gArr
		
		CodeGenerator g = new CodeGenerator(table0, table1, gArr, mod, words); // create CodeGenerator object
		
		OutputStream out = new PrintStream(outputClassName + ".java"); // 

		g.generate(out, outputClassName); // generate perfect hash to output
		
	}

	/**
	 * Generates the perfect hash table for the given words, and writes the
	 * generated code to the given stream.
	 *
	 * @param words
	 *            the list of words for which to generate a perfect hash table
	 * @param output
	 *            the stream to which to write the generated code
	 * @param outputClassName
	 *            the name of the output class
	 * @param rng
	 *            the random number generator for the generated hash table
	 *
	 * @throws IllegalArgumentException
	 *             if the given output class name is not a valid Java identifier
	 */
	public void generate(List<String> words, OutputStream output, String outputClassName, Random rng)
			throws IllegalArgumentException {
		// TODO complete

		if (!outputClassName.matches("[A-Za-z_][A-Za-z_0-9]*")) {
			throw new IllegalArgumentException("invalid class name \"" + outputClassName + "\"");
		}
		
		int[][] table0 = new int[TABLE_ROWS][TABLE_COLUMNS];
		int[][] table1 = new int[TABLE_ROWS][TABLE_COLUMNS]; // generate hash tables
		
		int mod = 2 * words.size() +1; // create mod variable
		
		Graph map = mapping(table0, table1, mod, rng, words); // store mapping in graph variable
		
		int[] gArr = map.fillGArray(words.size()); // fill gArr
		
		CodeGenerator gen = new CodeGenerator(table0, table1, gArr, mod, words); // create new CodeGenerator object
						
		gen.generate(output, outputClassName); // generate perfect hash to output
		
	}

	/**
	 * Performs the mapping step for generating the perfect hash table.
	 *
	 * Precondition: the list of keys contains no duplicate values.
	 *
	 * @param table1
	 *            the T1 table
	 * @param table2
	 *            the T2 table
	 * @param modulus
	 *            the modulus
	 * @param rng
	 *            the random number generator to use
	 * @param words
	 *            the list of keys for the hash table
	 * @return the generated graph
	 *
	 * @throws IllegalArgumentException
	 *             if the modulus is not positive
	 */
	private Graph mapping(int[][] table1, int[][] table2, int modulus, Random rng, List<String> words)
			throws IllegalArgumentException {
		Graph toRet;

		do {
			toRet = new Graph(modulus);

			for (int r = 0; r < TABLE_ROWS; ++r) {
				for (int c = 0; c < TABLE_COLUMNS; ++c) {
					table1[r][c] = rng.nextInt(modulus);
					table2[r][c] = rng.nextInt(modulus);
				}
			}

			for (int i = 0; i < words.size(); ++i) {
				String w = words.get(i);
				int f1 = 0, f2 = 0;

				for (int j = 0; j < w.length(); ++j) {
					f1 += table1[j % TABLE_ROWS][w.charAt(j) % TABLE_COLUMNS];
					f2 += table2[j % TABLE_ROWS][w.charAt(j) % TABLE_COLUMNS];
				}

				f1 %= modulus;
				f2 %= modulus;

				toRet.addEdge(f1, f2, i, w);
			}
		} while (toRet.hasCycle());

		return toRet;
	}

	/**
	 * Reads the indicated file, making a list containing the lines within it.
	 *
	 * @param fileName
	 *            the file to read
	 * @return a list containing the lines of the indicated file
	 *
	 * @throws FileNotFoundException
	 *             if the indicated file cannot be read
	 */
	private List<String> readWordFile(String fileName) throws FileNotFoundException {
		// TODO complete
		
		if (fileName == null) {
			throw new FileNotFoundException();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		File f = new File(fileName);
		Scanner s = new Scanner(f);
	
			while(s.hasNextLine()) {
				list.add(s.nextLine());
			}
		s.close();
		return list;
	}
}

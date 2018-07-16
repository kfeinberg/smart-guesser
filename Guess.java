package lab9;

import java.util.Arrays;

import lab7.Student;

/**
 * A Guess has-an array of integers.
 * 
 * Instead of representing a peg by a color, we will
 *   represent it by a small integer value,
 *   starting at 0.  This is the "model" for a peg ,and its
 *   color might be a "view" of a peg.
 *   
 * So in the standard game with 6 colors for pegs we have pegs
 * identified by the integers 0 through 5.
 *   
 *   
 * The array of a Guess specifies the pegs of a guess by
 *   their encoding number.
 *   
 * So the array
 * [ 4, 3, 5, 1 ]
 *  Has peg (of identity) 4 in the leftmost hole
 *      peg               3 in the second hole
 *      peg               5 in the third hole
 *      peg               1 in the rightmost hole
 *      
 * You do not need to validate the contents of the elements of the array.
 * This is a "friend" class for the project, in the sense that only trusted
 *    components create Guess instances.
 *      
 * @author Kalli Feinberg
 *
 */

public class Guess {
	
	private int [] guess;
	/**
	 * Capture the array of integers for use in other methods
	 * @param guess
	 */
	public Guess(int[] guess) {
		this.guess = guess;
	}
	
	/**
	 * 
	 * @return the size of the array
	 */
	public int size() {
		return this.guess.length;
	}
	
	/**
	 * Provides the peg value at the specified hole.  You do not
	 *  need to check that the specified hole is in-bounds for the
	 *  array.  If it's out of bounds, it's the caller's fault!
	 *  
	 * @param hole the index
	 * @return the value of the array at the specified index
	 */
	public int getChoice(int hole) {
		return this.guess[hole];
	}
	
	/**
	 * Provide a nice depiction of the array as a String
	 */
	@Override
	public String toString() {
		return Arrays.toString(this.guess);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(guess);
		return result;
	}

	@Override
	/**
	 * @param another object
	 * @return if the objects are equal
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guess other = (Guess) obj;
		if (!Arrays.equals(guess, other.guess))
			return false;
		return true;
	}

	//
	// You must generate hashCode and equals !!
	//

}
package lab9;
import lab9.implementations.HonestFeedback;
import java.util.LinkedList;
import java.util.List;
import lab9.providers.ProvidesGuess;
import java.util.HashSet;

public class SmartGuesser2 implements ProvidesGuess {

	private HonestFeedback feedback;
	private int [] intersectionHist;
	private int [] colorHist;
	public History history;
	private List<Guess> allCombinations;

	/**
	 * 
	 * @param properties
	 * @param feedback
	 */
	public SmartGuesser2 (GameProperties gp, HonestFeedback feedback) {
		this.feedback = feedback;
		history = new History (gp);
		this.intersectionHist = new int [12];
		this.colorHist = new int [12];
		List <Guess> allCombinations = new LinkedList<Guess>();
		this.allCombinations = allCombinations;
		for (int i = 0; i < intersectionHist.length; i++) {
			intersectionHist[i] = 100;
			colorHist[i] = 100;
		}
	}
	/**
	 * 
	 * @param four integers
	 * @return if none of the integers are the same
	 */
	public boolean isValid (int a, int b, int c, int d) {
		if ((a != b && a!=c) && (a!=d && b!=c) && (b!=d && c!=d)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @return the position of the value in allCombinations that will remove the most 
	 * other values if guessed
	 * 
	 */
	public int posMostInCommon () {
		int [] array = new int [allCombinations.size()];
		for (int i = 0; i < allCombinations.size(); i++) {
			array[i] = 1200;
			for (int posVal = 0; posVal < 4; posVal++) {
				for (int colorVal = 0; colorVal < 5; colorVal++) {
					if (colorVal >= posVal && (removedVals(allCombinations.get(i), posVal, colorVal) < array[i])) {
						array[i] = removedVals (allCombinations.get(i), posVal, colorVal);
					}
				}
			}

		}
		int maxVal = 0;
		int maxPos = 0;
		for (int k = 0; k < array.length; k++) {
			if (array[k] > maxVal) {
				maxPos = k;
				maxVal = array[k];
			}
		}
		return maxPos;
	}

	/**
	 * 
	 * @param guess - a guess
	 * @param posVal - a "match" value 
	 * @param colorVal - a "same color" value
	 * @return how many values in allCombinations that guess will remove if the feedback returns
	 * posVal and colorVal
	 */
	public int removedVals (Guess guess, int posVal, int colorVal) {
		int removedVals = 0;
		for (int i = 0; i < allCombinations.size(); i++) {
			if (numSamePosition (allCombinations.get(i), guess) != posVal || numIntersection (allCombinations.get(i), guess) != colorVal) {
				removedVals++;
			}

		}
		return removedVals;
	}
	
	/**
	 * 
	 * @param guess - one guess
	 * @param other - another guess
	 * @return how many pegs are in the same position in both of these guesses
	 */
	public int numSamePosition(Guess guess, Guess other) {
		int num = 0;
		for (int i = 0; i < other.size(); i++) {
			if (guess.getChoice(i) == other.getChoice(i)) {
				num++;
			}
		}
		return num;
	}
	/**
	 * 
	 * @param turn - a guess
	 * @param other - another guess
	 * @return how many color intersections these two guesses have
	 */
	public int numIntersection(Guess turn, Guess other ) {
		int intersections = 0;
		HashSet<Integer> set1 = new HashSet<Integer>();
		HashSet<Integer> set2 = new HashSet<Integer>();
		for (int i = 0; i < 4; i++) {
			set1.add(turn.getChoice(i));
			set2.add(other.getChoice(i));
		}
		for (int g: set1) {
			for (int f: set2) {
				if (g == f) {
					intersections++;
				}
			}
		}
		return intersections;
	}
	/**
	 * fills empty list allCombinations with all 360 possible codes in this MasterMind game
	 */
	public void fillAllCombinations () {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 6; k++) {
					for (int l = 0; l < 6; l++) {
						if (isValid (i, j, k, l)) {
							int [] array = {i, j, k, l};
							Guess guess = new Guess (array);
							allCombinations.add(guess);
						}
					}		
				}
			}
		}	
	}
	
	/**
	 * 
	 * @param a guess
	 * fills an array with the match value guess returned
	 */
	public void setIntersectionHist(Guess guess) {
		for (int i = 0; i < intersectionHist.length; i++) {
			if (intersectionHist[i] == 100) {
				intersectionHist[i] = feedback.numSamePosition(guess);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param a guess
	 * fills an array with the same color value returned
	 */
	public void setColorHist(Guess guess) {
		for (int i = 0; i < colorHist.length; i++) {
			if (colorHist[i] == 100) {
				colorHist[i] = feedback.numIntersection(guess);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param int pos
	 * @return int intersection value at this position
	 */
	public int getIntersectionHist (int pos) {
		return intersectionHist[pos];
	}
	
	/**
	 * @param int pos
	 * @return int intersection value at this position
	 */
	public int getColorHist(int pos) {
		return colorHist[pos];
	}
	
	/**
	 * 
	 * @param int turn value
	 * removes the values from allCombinations that couldn't possibly be solution based on the "match" value returned
	 */
	public void removeNumSamePos (int turn) {
		int numSamePos = getIntersectionHist(turn);
		Guess guess = history.getHistoryAt(turn);
		//no pegs in the same position as code
		int i = 0;
		while (i < allCombinations.size()) {
			if (numSamePosition(allCombinations.get(i), guess) != numSamePos) {
				allCombinations.remove(i);
				i+=0;
			}
			else {
				i++;
			}
		}
		allCombinations.remove(guess);
	}
	
	/**
	 * 
	 * @param int turn value
	 * removes the values from allCombinations that couldn't possibly be solution based on the "same color" value returned
	 */
	public void removeNumIntersection (int turn) {
		int numSameColor = getColorHist(turn);
		Guess guess = history.getHistoryAt(turn);
		int i = 0;
		while (i < allCombinations.size()) {
			if (numIntersection(allCombinations.get(i), guess) < numSameColor || numIntersection(allCombinations.get(i), guess) > numSameColor) {
				allCombinations.remove(i);
			}
			else i++;
		}
		allCombinations.remove(guess);
	}

	/**
	 * main code for making a guess
	 */
	@Override
	public Guess getGuess() {
		if (history.size() == 0) {
			fillAllCombinations();
			Guess guess = allCombinations.get(posMostInCommon());
			history.registerGuess(guess);
			setIntersectionHist (guess);
			setColorHist(guess);
			return guess;
		}
		else {
			removeNumIntersection(history.size()-1);
			removeNumSamePos(history.size()-1);
			Guess guess = allCombinations.get(posMostInCommon());
			history.registerGuess(guess);
			setIntersectionHist (guess);
			setColorHist(guess);
			return guess;
		}
	}
}








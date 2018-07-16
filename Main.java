package lab9;
import lab9.implementations.HonestFeedback;

import lab9.implementations.StaticGuessProvider;
import lab9.providers.ProvidesGuess;

public class Main {

	public static void main(String[] args) {
		GameProperties randomGame = new GameProperties("not random guesser");
		ProvidesGuess codeMaker2 = new StaticGuessProvider(randomGame);
		HonestFeedback feedback = new HonestFeedback(codeMaker2.getGuess());
		SmartGuesser guesser = new SmartGuesser(randomGame, feedback);
		//GameProperties notRandom = new GameProperties("Not Random");
		Controller rcontroller = new Controller(randomGame, codeMaker2, guesser);
		rcontroller.run();
	}
}

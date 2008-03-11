package com.ironiacorp.commons.spelling;

import org.gauner.jSpellCorrect.ToySpellingCorrector;

// http://developer.gauner.org/jspellcorrect/
// http://www.norvig.com/spell-correct.html
public class StatisticalSpellEngine extends SpellingEngine
{
	public StatisticalSpellEngine()
	{
		ToySpellingCorrector sc = new ToySpellingCorrector();
		// train some data from a text file
		sc.trainFile("/tmp/big.txt");
		// train a single word
		sc.trainSingle("some word");
		// get the best suggestion
		System.out.println(sc.correct("Cads"));
		System.out.println(sc.correct("Dok"));
		System.out.println(sc.correct("Speling"));
	}
}

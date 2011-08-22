package com.ironiacorp.spelling;

import org.gauner.jSpellCorrect.ToySpellingCorrector;

// http://developer.gauner.org/jspellcorrect/
// http://www.norvig.com/spell-correct.html
public class StatisticalSpellEngine implements SpellEngine
{
	private ToySpellingCorrector engine;
	
	public StatisticalSpellEngine()
	{
		engine = new ToySpellingCorrector();
		// train some data from a text file
		engine.trainFile("/tmp/big.txt");
		// train a single word
		engine.trainSingle("some word");
	}

	public boolean isCorrect(String word)
	{
		String correction = engine.correct(word);
		return word.equals(correction);
	}

	public String[] getSuggestions(String word)
	{
		return engine.correct(word);
	}
}
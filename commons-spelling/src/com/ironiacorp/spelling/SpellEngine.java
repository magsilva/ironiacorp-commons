package com.ironiacorp.spelling;

public interface SpellEngine
{
	boolean isCorrect(String word);
	
	String[] getSuggestions(String word);
}


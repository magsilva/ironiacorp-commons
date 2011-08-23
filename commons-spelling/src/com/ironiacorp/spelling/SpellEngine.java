package com.ironiacorp.spelling;

import java.util.List;

public interface SpellEngine
{
	boolean isCorrect(String word);
	
	List<String> getSuggestions(String word);
}


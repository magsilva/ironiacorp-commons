package com.ironiacorp.commons.spelling;

public class SpellingStrategyFactory<T extends LanguageDescriptor, U extends SpellingStrategy> extends AbstractStrategyFactory<T, U>
{
	private SpellingStrategyFactory()
	{
	}

	static SpellingStrategyFactory<LanguageDescriptor, SpellingStrategy> instance;

	public static Factory<LanguageDescriptor, SpellingStrategy> getInstance()
	{
		if (null == instance) {
			instance = new SpellingStrategyFactory<LanguageDescriptor, SpellingStrategy>();
		}
		return instance;
	}

	public U create(T language)
	{
		U strategy = (U) new SimpleSpellingStrategy();
		strategy.add(StatisticalSpellEngine.class);
		return strategy;
	}
}
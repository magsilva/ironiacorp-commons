package com.ironiacorp.commons.spelling;

public class SpellEngineFactory<T extends LanguageDescriptor, U extends SpellingEngine> extends AbstractFactory<T, U>
{
	private SpellEngineFactory()
	{
	}

	static SpellEngineFactory<LanguageDescriptor, SpellingEngine> instance;

	public static SpellEngineFactory<LanguageDescriptor, SpellingEngine> getInstance()
	{
		if (null == instance) {
			instance = new SpellEngineFactory<LanguageDescriptor, SpellingEngine>();
		}
		return instance;
	}

	public U create(T language)
	{
		Factory<LanguageDescriptor, SpellingStrategy> stratFactory = SpellingStrategyFactory.getInstance();
		Strategy strategy = stratFactory.create(language);
		return (U) new StatisticalSpellEngine();
	}
}

package com.ironiacorp.spelling;

public class SpellingEngine
{
	Object payload;

	public Object getPayload()
	{
		return payload;
	}

	public void configure(Object obj)
	{
		payload = obj;
	}

	public void send(SpellingStrategy ss)
	{
		ss.spellCheck();
	}
}


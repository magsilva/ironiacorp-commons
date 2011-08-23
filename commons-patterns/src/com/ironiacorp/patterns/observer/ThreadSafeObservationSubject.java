package com.ironiacorp.patterns.observer;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeObservationSubject extends BaseObservationSubject
{
	public ThreadSafeObservationSubject()
	{
		super();
	}

	@Override
	protected Collection<Observer> initObservers()
	{
		return new CopyOnWriteArrayList<Observer>();
	}
}

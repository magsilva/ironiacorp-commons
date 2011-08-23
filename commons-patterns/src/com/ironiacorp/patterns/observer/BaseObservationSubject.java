/*
Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.ironiacorp.patterns.observer;

import java.util.Collection;
import java.util.Iterator;

public abstract class BaseObservationSubject implements ObservationSubject
{
	private Collection<Observer> observers;

	protected abstract Collection<Observer> initObservers();
	
	public BaseObservationSubject()
	{
		observers = initObservers();
	}
	
	@Override
	public void addObserver(Observer observer)
	{
		observers.add(observer);
	}

	@Override
	public void deleteObserver(Observer observer)
	{
		observers.remove(observer);
	}

	@Override
	public void notifyObservers()
	{
		notifyObservers(new ChangeSet(this));
	}
	
	@Override
	public void notifyObservers(ChangeSet change)
	{
		if (change == null) {
			throw new IllegalArgumentException("Not change set has been provided");
		}
		
		Iterator<Observer> i = observers.iterator();
		while (i.hasNext()) {
			Observer observer = i.next();
			observer.notify(change);
		}
	}
}

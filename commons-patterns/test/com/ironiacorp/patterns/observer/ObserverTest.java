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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ObserverTest
{
	private class DummyObserver implements Observer
	{
		private List<ChangeSet> changesets = new ArrayList<ChangeSet>();
		
		public List<ChangeSet> getChangeSets()
		{
			return changesets;
		}
		
		@Override
		public void notify(ChangeSet changeSet) {
			changesets.add(changeSet);
		}
	};
	
	private DummyObserver observer1;

	private DummyObserver observer2;

	
	@Before
	public void setUp()
	{
		observer1 = new DummyObserver();
		observer2 = new DummyObserver();
	}

	
	@Test
	public void testNotify()
	{
		ThreadSafeObservationSubject subject1 = new ThreadSafeObservationSubject();
		ThreadSafeObservationSubject subject2 = new ThreadSafeObservationSubject();
		
		subject1.addObserver(observer1);
		subject1.addObserver(observer2);
		subject2.addObserver(observer1);
		
		subject1.notifyObservers();
		subject1.notifyObservers();
		subject2.notifyObservers();
		Iterator<ChangeSet> i = observer1.getChangeSets().iterator(); 
		assertEquals(i.next().getSubject(), subject1);
		assertEquals(i.next().getSubject(), subject1);
		assertEquals(i.next().getSubject(), subject2);
		assertFalse(i.hasNext());
		
		i = observer2.getChangeSets().iterator(); 
		assertEquals(i.next().getSubject(), subject1);
		assertEquals(i.next().getSubject(), subject1);
		assertFalse(i.hasNext());
	}

}

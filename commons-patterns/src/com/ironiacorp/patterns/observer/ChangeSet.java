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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChangeSet
{
	private final ObservationSubject subject;
	
	private List<Change> changes;
	
	public ChangeSet(ObservationSubject subject)
	{
		if (subject == null) {
			throw new IllegalArgumentException("Must define a subject (the object that has been changed)");
		}
		this.subject = subject;
		changes = new LinkedList<Change>();
	}
	
	public ObservationSubject getSubject()
	{
		return subject;
	}
	
	public void add(Change change)
	{
		changes.add(change);
	}
	
	public void remove(Change change)
	{
		changes.remove(change);
	}
	
	public Iterator<Change> iterator()
	{
		return changes.iterator();
	}
}

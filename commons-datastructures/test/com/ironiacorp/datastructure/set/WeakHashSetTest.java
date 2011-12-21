/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.datastructure.set;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WeakHashSetTest
{
	private WeakHashSet<String> set;

	@Before
	public void setUp() throws Exception
	{
		set = new WeakHashSet<String>();
	}

	@Test
	public void testIterator_NonEmpty()
	{
		set.add("test1");
		set.add("test2");
		Iterator<String> i = set.iterator();
		assertTrue(i.hasNext());
		assertEquals("test1", i.next());
		assertTrue(i.hasNext());
		assertEquals("test2", i.next());
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testIterator_Remove()
	{
		set.add("test1");
		set.add("test2");
		Iterator<String> i = set.iterator();
		assertTrue(i.hasNext());
		assertEquals("test1", i.next());
		i.remove();
		assertFalse(set.contains("test1"));
		assertTrue(i.hasNext());
		assertEquals("test2", i.next());
		i.remove();
		assertFalse(set.contains("test2"));
		assertFalse(i.hasNext());
	}

	@Test
	public void testIterator_Empty()
	{
		Iterator<String> i = set.iterator();
		assertFalse(i.hasNext());
	}

	
	@Test
	public void testContainsObject()
	{
		assertFalse(set.contains("test"));
	}

	@Test
	public void testAdd()
	{
		set.add("test");
		assertTrue(set.contains("test"));
	}

	@Test
	public void testRemoveObject()
	{
		set.add("test");
		set.remove("test");
		assertFalse(set.contains("test"));
	}

	@Test
	public void testIsEmpty_Empty() {
		assertTrue(set.isEmpty());
	}

	@Test
	public void testIsEmpty_NonEmpty() {
		set.add("test");
		assertFalse(set.isEmpty());
	}
	
	@Ignore
	@Test
	public void testRefresh()
	{
		WeakHashSet<List<String>> set2 = new WeakHashSet<List<String>>();
		List<String> list = new ArrayList<String>();
		set2.add(list);
		assertTrue(set2.contains(list));
		list = null;
		for (int i = 0; i < 100; i++) {
			System.gc();
			set2.refresh();
		}
		assertTrue(set2.isEmpty());
	}
}

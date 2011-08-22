/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2010 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
*/


package com.ironiacorp.datastructure.set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.datastructure.array.ArrayUtil;

public class IdentityMethodSetTest
{
	private IdentityMethodSet<String, String> set;
	
	@Before
	public void setUp() throws Exception
	{
		set = new IdentityMethodSet<String, String>();
	}

	
	@Test
	public void testNew_NoIdentityMethod()
	{
		new IdentityMethodSet<String, String>();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testNew_InvalidIdentityMethod_NoSuchMethod()
	{
		IdentityMethodSet<String, String> set2 = new IdentityMethodSet<String, String>("fkljadfkls");
		set2.add("ABC");
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testNew_InvalidIdentityMethod_UnsupportedOperation() throws Exception
	{
		InetAddress clazz = mock(InetAddress.class);
		when(clazz.toString()).thenThrow(new NullPointerException());
		IdentityMethodSet<String, InetAddress> set2 = new IdentityMethodSet<String, InetAddress>("toString");
		set2.add(clazz);
	}

	
	@Test
	public void testAdd_NoIdentityMethod()
	{
		IdentityMethodSet<String, String> set2 = new IdentityMethodSet<String, String>();
		set2.add("ABC");
		assertTrue(set2.contains("ABC"));
	}

	
	@Test
	public void testAdd()
	{
		String abc = "test 1 2 3";
		set.add(abc);
		assertEquals(abc, set.getByKey(set.getIdentity(abc)));
		assertEquals(abc, set.add(abc));
	}

	@Test
	public void testAddAll_EmptySet()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		Collection<String> collectionObjectsBefore = ArrayUtil.toCollection(arrayObjects); 
		Collection<? extends String> collectionObjectsAfter = set.addAll(collectionObjectsBefore);
		assertEquals(collectionObjectsBefore, collectionObjectsAfter);
		assertTrue(set.containsAll(collectionObjectsAfter));
		assertTrue(set.containsAll(collectionObjectsBefore));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddAll_NullSet()
	{
		set.addAll(null);
	}
	
	@Test
	public void testAddAll_SetWithIntersection_String()
	{
		String[] arrayObjects1 = {"test", "1", "2", "3"};
		String[] arrayObjects2 = {"2", "3", "4"};
		Collection<String> collectionObjects1 = ArrayUtil.toCollection(arrayObjects1); 
		set.addAll(collectionObjects1);
		assertTrue(set.containsAll(collectionObjects1));
		
		Collection<String> collectionObjects2 = ArrayUtil.toCollection(arrayObjects2); 
		Collection<? extends String> result = set.addAll(collectionObjects2);
		assertEquals(collectionObjects2, result);
		assertTrue(arrayObjects1[0] == set.getByKey(set.getIdentity(arrayObjects1[0])));
		assertTrue(arrayObjects1[1] == set.getByKey(set.getIdentity(arrayObjects1[1])));
		assertTrue(arrayObjects1[2] == set.getByKey(set.getIdentity(arrayObjects1[2])));
		assertTrue(arrayObjects1[3] == set.getByKey(set.getIdentity(arrayObjects1[3])));
		// Strings are an special case... they hold the identity if they are equal
		// assertFalse(arrayObjects2[0] == set.get("2"));
		// assertFalse(arrayObjects2[1] == set.get("3"));
		assertTrue(arrayObjects2[0] == set.getByKey(set.getIdentity(arrayObjects1[2])));
		assertTrue(arrayObjects2[0] == set.getByKey(set.getIdentity(arrayObjects2[0])));
		
		assertTrue(set.containsAll(collectionObjects1));
		assertTrue(set.containsAll(collectionObjects2));
	}

	@Test
	public void testAddAll_SetWithIntersection_File()
	{
		IdentityMethodSet<String, File> set = new IdentityMethodSet<String, File>("getAbsolutePath");

		File[] arrayObjects1 = {
						new File("test"),
						new File("1"),
						new File("2"),
						new File("3")
		};
		File[] arrayObjects2 = {
						new File("2"),
						new File("3"),
						new File("4")
		};
		Collection<File> collectionObjects1 = ArrayUtil.toCollection(arrayObjects1); 
		set.addAll(collectionObjects1);
		assertTrue(set.containsAll(collectionObjects1));
		
		Collection<File> collectionObjects2 = ArrayUtil.toCollection(arrayObjects2); 
		Collection<? extends File> result = set.addAll(collectionObjects2);
		assertEquals(collectionObjects2, result);
		assertTrue(arrayObjects1[0] == set.getByKey(arrayObjects1[0].getAbsolutePath()));
		assertTrue(arrayObjects1[1] == set.getByKey(arrayObjects1[1].getAbsolutePath()));
		assertTrue(arrayObjects1[2] == set.getByKey(arrayObjects1[2].getAbsolutePath()));
		assertTrue(arrayObjects1[3] == set.getByKey(arrayObjects1[3].getAbsolutePath()));
		assertFalse(arrayObjects2[0] == set.getByKey(arrayObjects2[0].getAbsolutePath()));
		assertFalse(arrayObjects2[1] == set.getByKey(arrayObjects2[1].getAbsolutePath()));
		assertTrue(arrayObjects2[2] == set.getByKey(arrayObjects2[2].getAbsolutePath()));
		
		assertTrue(set.containsAll(collectionObjects1));
		assertTrue(set.containsAll(collectionObjects2));
	}

	
	@Test
	public void testContains_ValidValue()
	{
		set.add("a");
		assertTrue(set.contains("a"));
	}

	@Test
	public void testContains_InvalidValue_NonExistent()
	{
		String element = "a";
		set.add(element);
		assertTrue(set.contains(element));
		assertEquals(element, set.getByKey(set.getIdentity(element)));
		assertFalse(set.contains("B"));
	}

	@Test
	public void testContainsAll()
	{
		String[] arrayObjects1 = {"test", "1", "2", "3"};
		String[] arrayObjects2 = {"2", "3", "4"};
		Collection<String> collectionObjects1 = ArrayUtil.toCollection(arrayObjects1); 
		Collection<String> collectionObjects2 = ArrayUtil.toCollection(arrayObjects2); 
		set.addAll(collectionObjects1);
		assertTrue(set.containsAll(collectionObjects1));
		assertFalse(set.containsAll(collectionObjects2));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testAdd_InvalidValue_Null()
	{
		set.add(null);
	}

	
	@Test
	public void testGet_Invalid()
	{
		assertNull(set.get("ABC"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGet_Invalid_Null()
	{
		set.get(null);
	}
	
	@Test
	public void testGet_Valid()
	{
		String element = "a";
		set.add(element);
		assertTrue(element == set.get("a"));
		assertFalse(element == set.get("A"));
	}

	
	@Test
	public void testGetByKey_Invalid()
	{
		assertNull(set.getByKey("ABC"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetByKey_Invalid_Null()
	{
		set.getByKey(null);
	}
	
	@Test
	public void testGetByKey_Valid()
	{
		String element = "a";
		set.add(element);
		assertTrue(element == set.getByKey(set.getIdentity("a")));
	}

	@Test
	public void testGetByValue_Valid()
	{
		String element = "a";
		set.add(element);
		assertEquals(element, set.getByValue("a"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetByValue_Invalid()
	{
		set.getByValue(null);
	}
	
	@Test
	public void testIterator_EmptySet()
	{
		Iterator<String> i = set.iterator();
		if (i.hasNext()) {
			fail();
		}
	}

	@Test
	public void testIterator_ValidSet()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));

		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String element = i.next();
			if (! set.contains(element)) {
				fail();
			}
		}
	}
	
	@Test
	public void testRemoveByValue()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));
		set.removeByValue(arrayObjects[0]);
		assertFalse(set.contains(arrayObjects[0]));
	}

	@Test
	public void testRemoveByKey()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));
		set.removeByKey(set.getIdentity(arrayObjects[0]));
		assertFalse(set.contains(arrayObjects[0]));
	}

	
	@Test
	public void testRemoveAll()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));
		set.removeAll(ArrayUtil.toCollection(arrayObjects));
		assertFalse(set.contains(arrayObjects[0]));
		assertFalse(set.contains(arrayObjects[1]));
		assertFalse(set.contains(arrayObjects[2]));
		assertFalse(set.contains(arrayObjects[3]));
	}

	@Test
	public void testRemove_Invalid_Inexistent()
	{
		assertNull(set.remove("test"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemove_Invalid_Null()
	{
		set.remove(null);
	}

	@Test
	public void testRemoveByKey_Invalid_Inexistent()
	{
		assertNull(set.removeByKey("test"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveBByKey_Invalid_Null()
	{
		set.removeByKey(null);
	}

	
	@Test
	public void testIsEmpty()
	{
		assertTrue(set.isEmpty());
	}

	@Test
	public void testIsEmpty2()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));
		set.removeAll(ArrayUtil.toCollection(arrayObjects));
		assertTrue(set.isEmpty());
	}

	@Test
	public void testSize_Empty()
	{
		assertEquals(0, set.size());
	}

	@Test
	public void testSize_NonEmpty()
	{
		String[] arrayObjects = {"test", "1", "2", "3"};
		set.addAll(ArrayUtil.toCollection(arrayObjects));
		assertEquals(arrayObjects.length, set.size());
	}
	
	@Test
	public void testContains_Null()
	{
		assertFalse(set.contains(null));
	}
}

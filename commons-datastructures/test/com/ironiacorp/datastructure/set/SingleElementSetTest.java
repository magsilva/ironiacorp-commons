package com.ironiacorp.datastructure.set;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SingleElementSetTest {
	
	SingleElementSet<String> set;
	
	boolean result;

	@Before
	public void setUp() throws Exception {
		set = new SingleElementSet<String>();
	}

	@Test
	public void testConstructor() {
		set = new SingleElementSet<String>("test");
		assertTrue(set.contains("test"));
	}
	
	@Test
	public void testSize_Empty() {
		assertEquals(0, set.size());
	}

	@Test
	public void testSize_Full() {
		set.add("a");
		assertEquals(1, set.size());
	}


	@Test
	public void testIsEmpty_Empty() {
		assertTrue(set.isEmpty());
	}

	@Test
	public void testIsEmpty_Full() {
		set.add("a");
		assertFalse(set.isEmpty());
	}

	@Test
	public void testIsEmpty_Full_Empty() {
		set.add("a");
		set.clear();
		assertTrue(set.isEmpty());
	}
	
	@Test
	public void testContains_Fail() {
		assertFalse(set.contains("a"));
	}
	@Test
	public void testContains_Null() {
		assertFalse(set.contains(null));
	}
	
	@Test
	public void testContains_Success1() {
		set.add("a");
		assertTrue(set.contains("a"));
	}
	
	@Test
	public void testContains_Success2() {
		set.add("a");
		set.clear();
		assertFalse(set.contains("a"));
	}

	@Test
	public void testIterator_Empty() {
		Iterator<String> i = set.iterator();
		assertFalse(i.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void testIterator_Empty2() {
		Iterator<String> i = set.iterator();
		i.next();
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testIterator_Empty3() {
		Iterator<String> i = set.iterator();
		try {
			i.next();
		} catch (Exception e) {
		}
		i.next();
	}

	@Test
	public void testIterator_Remove_Full() {
		set.add("a");
		Iterator<String> i = set.iterator();
		i.next();
		i.remove();
		assertTrue(set.isEmpty());
	}

	@Test(expected=IllegalStateException.class)
	public void testIterator_Remove_Empty() {
		Iterator<String> i = set.iterator();
		i.remove();
	}

	
	@Test
	public void testIterator_Full() {
		set.add("a");
		Iterator<String> i = set.iterator();
		int count = 0;
		assertTrue(i.hasNext());
		while (i.hasNext()) {
			count++;
			String text = i.next();
			assertEquals("a", text);
		}
		assertEquals(1, count);
	}

	@Test
	public void testToArray_Empty() {
		String[] expected = new String[0];
		Object[] result = set.toArray();
		assertEquals(0, result.length);
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testToArray_Full() {
		String[] expected = new String[1];
		expected[0] = "a";
		set.add("a");
		Object[] result = set.toArray();
		assertEquals("a", result[0]);
	}

	@Test
	public void testToArrayTArray_Empty() {
		String[] expected = new String[0];
		String[] result = (String[]) set.toArray(new String[0]);
		assertArrayEquals(expected, result);
	}

	@Test
	public void testToArrayTArray_Full_EmptyResultArray() {
		String[] expected = new String[1];
		expected[0] = "a";
		set.add("a");
		String[] result = (String[]) set.toArray(new String[0]);
		assertArrayEquals(expected, result);
	}

	@Test
	public void testToArrayTArray_Full_BigResultArray() {
		String[] expected = new String[1];
		String[] seed = new String[10];
		expected[0] = "a";
		set.add("a");
		String[] result = (String[]) set.toArray(seed);
		assertSame(seed, result);
		assertEquals(expected[0], result[0]);
		assertEquals(null, result[1]);
	}

	
	@Test
	public void testAdd() {
		result = set.add("a");
		assertTrue(result);
		assertFalse(set.isEmpty());
		assertTrue(set.contains("a"));
	}

	@Test
	public void testAdd_Full() {
		set.add("a");
		result = set.add("a");
		assertFalse(result);
		assertFalse(set.isEmpty());
		assertTrue(set.contains("a"));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAdd_Full2_1() {
		set.add("a");
		set.add("b");
	}

	@Test
	public void testAdd_Full2_2() {
		try {
			set.add("a");
			set.add("b");
		} catch (UnsupportedOperationException e) {
			assertTrue(set.contains("a"));
		}
	}

	
	@Test
	public void testRemove() {
		set.add("a");
		result = set.remove("a");
		assertTrue(result);
		assertTrue(set.isEmpty());
		assertFalse(set.contains("a"));
	}

	@Test
	public void testRemoveFail() {
		set.add("a");
		result = set.remove("b");
		assertFalse(result);
		assertFalse(set.isEmpty());
		assertTrue(set.contains("a"));
	}
	
	@Test
	public void testContainsAll_Fail() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set2.add("a");
		set.add("a");
		assertFalse(set.containsAll(set2));
	}

	@Test
	public void testContainsAll_Success() {
		Set<String> set2 = new HashSet<String>();
		set2.add("a");
		set.add("a");
		assertTrue(set.containsAll(set2));
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddAll() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set2.add("a");
		set.addAll(set2);
	}

	@Test
	public void testRetainAll_Success() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set2.add("a");
		set.add("b");
		result = set.retainAll(set2);
		assertTrue(result);
		assertTrue(set.isEmpty());
	}

	@Test
	public void testRetainAll_Failure() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set2.add("a");
		set.add("a");
		result = set.retainAll(set2);
		assertFalse(result);
		assertFalse(set.isEmpty());
		assertTrue(set.contains("a"));
	}
	
	@Test
	public void testRemoveAll_Success() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set2.add("a");
		set.add("a");
		result = set.removeAll(set2);
		assertTrue(result);
		assertTrue(set.isEmpty());
	}

	@Test
	public void testRemoveAll_Fail() {
		Set<String> set2 = new HashSet<String>();
		set2.add("abc");
		set2.add("def");
		set.add("a");
		result = set.removeAll(set2);
		assertFalse(result);
		assertFalse(set.isEmpty());
		assertTrue(set.contains("a"));
	}

	@Test
	public void testClear_Empty() {
		set.clear();
		assertTrue(set.isEmpty());
	}

	@Test
	public void testClear_Full() {
		set.add("a");
		set.clear();
		assertTrue(set.isEmpty());
	}
	
	@Test
	public void testHashCode_Empty() {
		assertEquals(31, set.hashCode());
	}
	
	@Test
	public void testHashCode_Full() {
		set.add("a");
		assertTrue(31 != set.hashCode());
	}
	
	@Test
	public void testEquals_Empty_Empty()
	{
		SingleElementSet<String> set2 = new SingleElementSet<String>();
		assertTrue(set.equals(set2));
	}
	
	@Test
	public void testEquals_Empty_Full()
	{
		SingleElementSet<String> set2 = new SingleElementSet<String>();
		set2.add("a");
		assertFalse(set.equals(set2));
	}
	
	@Test
	public void testEquals_Full_Empty()
	{
		SingleElementSet<String> set2 = new SingleElementSet<String>();
		set.add("a");
		assertFalse(set.equals(set2));
	}

	@Test
	public void testEquals_Empty_Null()
	{
		assertFalse(set.equals(null));
	}
	
	@Test
	public void testEquals_Full_Null()
	{
		set.add("a");
		assertFalse(set.equals(null));
	}

	@Test
	public void testEquals_Same()
	{
		assertTrue(set.equals(set));
	}

	@Test
	public void testEquals_Different()
	{
		assertFalse(set.equals("a"));
	}

}

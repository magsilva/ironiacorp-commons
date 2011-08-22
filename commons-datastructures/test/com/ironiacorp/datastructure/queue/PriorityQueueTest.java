package com.ironiacorp.datastructure.queue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {
	
	private PriorityQueue<String> queue;

	@Before
	public void setUp() throws Exception {
		queue = new PriorityQueue<String>();
	}

	@Test
	public void testSize_Empty()
	{
		assertEquals(0, queue.size());
	}

	@Test
	public void testSize_NonEmpty()
	{
		queue.add("test");
		assertEquals(1, queue.size());
	}

	
	@Test
	public void testClear() {
		queue.add("test");
		queue.clear();
		assertEquals(0, queue.size());
	}

	@Test
	public void testAddE() {
		queue.add("test");
		assertTrue(queue.contains("test"));
	}

	@Test
	public void testInsert() {
		queue.add("test1");
		queue.insert("test2", 7);
		assertTrue(queue.contains("test1"));
		assertTrue(queue.contains("test2"));
		assertEquals("test2", queue.getFirst());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInsert_Err_LowerLimit() {
		queue.insert("test2", -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInsert_Err_UpperLimit() {
		queue.insert("test2", 10);
	}

	
	@Test
	public void testGet() {
		queue.add("test1");
		queue.insert("test2", 7);
		assertEquals("test2", queue.get(0));
		assertEquals("test1", queue.get(1));
	}

	@Test(expected=NoSuchElementException.class)
	public void testGet_2() {
		queue.add("test1");
		queue.insert("test2", 7);
		assertEquals("test2", queue.get(0));
		assertEquals("test1", queue.get(1));
		queue.get(10);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGet_3() {
		queue.get(-1);
	}
	
	@Test
	public void testRemoveFirst() {
		queue.add("test1");
		queue.insert("test2", 7);
		queue.removeFirst();
		assertFalse(queue.contains("test2"));
		assertTrue(queue.contains("test1"));
	}
	
	@Test
	public void testIterator()
	{
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		Iterator<String> i = queue.iterator();
		assertEquals("test1", i.next());
		assertEquals("test2", i.next());
		assertEquals("test3", i.next());
	}

	@Test
	public void testIterator_WithPriority()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		assertEquals("test3", i.next());
		assertEquals("test2", i.next());
		assertEquals("test1", i.next());
	}

	@Test
	public void testIterator_WithEmptyPriorityList()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.removeFirst();
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		assertEquals("test3", i.next());
		assertEquals("test1", i.next());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testIterator_ElementNotFound()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		assertEquals("test3", i.next());
		assertEquals("test2", i.next());
		assertEquals("test1", i.next());
		i.next();
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testIterator_ModifiedQueue()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		queue.insert("test4", 7);
		i.next();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIterator_Remove_1()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		i.next();
		i.remove();
		i.remove();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIterator_Remove_2()
	{
		queue.insert("test1", 0);
		queue.insert("test2", 5);
		queue.insert("test3", 9);
		Iterator<String> i = queue.iterator();
		i.remove();
	}
	
	@Test
	public void testToString_NonEmpty()
	{
		queue.insert("test1", 9);
		queue.insert("test2", 5);
		String result = queue.toString();
		assertEquals("{9:[test1],8:,7:,6:,5:[test2],4:,3:,2:,1:,0:}", result);
	}
	
	@Test
	public void testToString_Empty()
	{
		String result = queue.toString();
		assertEquals("{9:,8:,7:,6:,5:,4:,3:,2:,1:,0:}", result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testQueue_Count()
	{
		new PriorityQueue<String>(0);
	}
	
	@Test
	public void testQueue_Col()
	{
		List<String> collection = new ArrayList<String>();
		collection.add("test1");
		collection.add("test2");
		collection.add("test3");
		collection.add("test4");
		queue = new  PriorityQueue<String>(collection);
		assertTrue(queue.contains("test1"));
		assertTrue(queue.contains("test2"));
		assertTrue(queue.contains("test3"));
		assertTrue(queue.contains("test4"));
	}
	
	@Test
	public void testIsEmpty_Empty()
	{
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsEmpty_NonEmpty()
	{
		queue.add("test");
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void testRemoveAll()
	{
		List<String> collection = new ArrayList<String>();
		collection.add("test1");
		collection.add("test2");
		collection.add("test3");
		collection.add("test4");

		queue.add("test1");
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		queue.add("test4");
		queue.add("test5");
		
		queue.removeAll(collection);
		
		assertFalse(queue.contains("test1"));
		assertFalse(queue.contains("test2"));
		assertFalse(queue.contains("test3"));
		assertFalse(queue.contains("test4"));
		assertTrue(queue.contains("test5"));
	}
	
	@Test
	public void testRemove()
	{
		queue.add("test1");
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		queue.add("test4");
		queue.add("test5");
		queue.remove("test1");
		
		assertFalse(queue.contains("test1"));
		assertTrue(queue.contains("test2"));
		assertTrue(queue.contains("test3"));
		assertTrue(queue.contains("test4"));
		assertTrue(queue.contains("test5"));
	}
	
	
	@Test
	public void testContainsAll_Ok()
	{
		List<String> collection = new ArrayList<String>();
		collection.add("test1");
		collection.add("test2");
		collection.add("test3");
		collection.add("test4");

		queue.add("test1");
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		queue.add("test4");
		queue.add("test5");
		
		assertTrue(queue.containsAll(collection));
	}
	
	@Test
	public void testContainsAll_Err()
	{
		List<String> collection = new ArrayList<String>();
		collection.add("test1");
		collection.add("test2");
		collection.add("test3");
		collection.add("test4");
		collection.add("test6");

		queue.add("test1");
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		queue.add("test4");
		queue.add("test5");
		
		assertFalse(queue.containsAll(collection));
	}
	
	@Test
	public void testToArray_1()
	{
		String[] words = {"test1", "test2", "test3"};
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		assertArrayEquals(words, queue.toArray());
	}
	
	@Test
	public void testToArray_2()
	{
		String[] words = {"test1", "test2", "test3"};
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		assertArrayEquals(words, queue.toArray(words));
	}

	@Test
	public void testToArray_3()
	{
		String[] words = {"test1", "test2", "test3"};
		String[] inputArray = new String[1];
		String[] outputArray;
		
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		outputArray = queue.toArray(inputArray);
		assertArrayEquals(words, outputArray);
		assertNotSame(inputArray, outputArray);
	}
	
	@Test
	public void testToArray_4()
	{
		String[] words = {"test1", "test2", "test3"};
		String[] inputArray = new String[10];
		String[] outputArray;
		
		queue.add("test1");
		queue.add("test2");
		queue.add("test3");
		outputArray = queue.toArray(inputArray);
		assertEquals(words[0], outputArray[0]);
		assertEquals(words[1], outputArray[1]);
		assertEquals(words[2], outputArray[2]);
		assertNull(outputArray[3]);
		assertSame(inputArray, outputArray);
	}
}

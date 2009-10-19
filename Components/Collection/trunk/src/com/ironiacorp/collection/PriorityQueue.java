package com.ironiacorp.collection;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PriorityQueue<E> extends AbstractList<E> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY_COUNT = 10;
	private final static int DEFAULT_PRIORITY = 0;

	private List<E>[] queue;

	public PriorityQueue()
	{
		this(DEFAULT_PRIORITY_COUNT);
	}

	public PriorityQueue(Collection<E> col)
	{
		this(col, DEFAULT_PRIORITY_COUNT);
	}

	public PriorityQueue(int count)
	{
		this(null, count);
	}

	@SuppressWarnings("unchecked")
	public PriorityQueue(Collection<E> col, int count)
	{
		if (count <= 0) {
			throw new IllegalArgumentException("Illegal priority count: " + count);
		}
		queue = new List[count];
		if (col != null) {
			addAll(col);
		}
	}

	public boolean add(E element)
	{
		insert(element, DEFAULT_PRIORITY);
		return true;
	}

	public void insert(E element, int priority)
	{
		if (priority < 0) {
			throw new IllegalArgumentException("Illegal priority: " + priority);
		}
		if (queue[priority] == null) {
			queue[priority] = new LinkedList<E>();
		}
		queue[priority].add(element);
		modCount++;
	}

	public Object getFirst()
	{
		return iterator().next();
	}

	public E get(int index)
	{
		if (index < 0) {
			throw new IllegalArgumentException("Illegal index: " + index);
		}
		Iterator<E> iter = iterator();
		int pos = 0;
		while (iter.hasNext()) {
			if (pos == index) {
				return iter.next();
			} else {
				pos++;
			}
		}
		return null;
	}

	public void clear()
	{
		for (int i = 0, n = queue.length; i < n; i++) {
			queue[i].clear();
		}
	}

	public Object removeFirst()
	{
		Iterator<E> iter = iterator();
		Object obj = iter.next();
		iter.remove();
		return obj;
	}

	public int size()
	{
		int size = 0;
		for (int i = 0, n = queue.length; i < n; i++) {
			if (queue[i] != null) {
				size += queue[i].size();
			}
		}
		return size;
	}

	public Iterator<E> iterator()
	{
		Iterator<E> iter = new Iterator<E>()
		{
			int expectedModCount = modCount;
			int priority = queue.length - 1;
			int count = 0;

			// Used to prevent successive remove() calls
			int lastRet = -1;

			Iterator<E> tempIter;

			// Get iterator for highest priority
			{
				if (queue[priority] == null) {
					tempIter = null;
				} else {
					tempIter = queue[priority].iterator();
				}
			}

			private final void checkForComodification()
			{
				if (modCount != expectedModCount) {
					throw new ConcurrentModificationException();
				}
			}

			public boolean hasNext()
			{
				return count != size();
			}

			public E next()
			{
				while (true) {
					if ((tempIter != null) && (tempIter.hasNext())) {
						E next = tempIter.next();
						checkForComodification();
						lastRet = count++;
						return next;
					} else {
						// Get next iterator
						if (--priority < 0) {
							checkForComodification();
							throw new NoSuchElementException();
						} else {
							if (queue[priority] == null) {
								tempIter = null;
							} else {
								tempIter = queue[priority].iterator();
							}
						}
					}
				}
			}

			public void remove()
			{
				if (lastRet == -1) {
					throw new IllegalStateException();
				}
				checkForComodification();
				tempIter.remove();
				count--;
				lastRet = -1;
				expectedModCount = modCount;
			}
		};
		return iter;
	}

	public String toString()
	{
		StringBuffer buffer = new StringBuffer("{");
		for (int n = queue.length - 1, i = n; i >= 0; --i) {
			if (i != n) {
				buffer.append(",");
			}
			buffer.append(i + ":");
			if ((queue[i] != null) && (queue[i].size() > 0)) {
				buffer.append(queue[i].toString());
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
}

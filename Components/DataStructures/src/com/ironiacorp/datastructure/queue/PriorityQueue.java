package com.ironiacorp.datastructure.queue;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PriorityQueue<E> implements Serializable, Collection<E>
{
	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default priority count.
	 */
	private final static int DEFAULT_PRIORITY_COUNT = 10;
	
	/**
	 * Default priority for a new element.
	 */
	private final static int DEFAULT_PRIORITY = 0;

	private int modCount;
	
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
		addAll(col);
	}

	public boolean add(E element)
	{
		insert(element, DEFAULT_PRIORITY);
		return true;
	}

	public void insert(E element, int priority)
	{
		if (priority < 0 || priority >= queue.length) {
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
				iter.next();
				pos++;
			}
		}

		throw new NoSuchElementException();
	}

	public void clear()
	{
		for (int i = 0, n = queue.length; i < n; i++) {
			if (queue[i] != null) {
				queue[i].clear();
			}
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

			Iterator<E> tempIter = null;

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
					if (tempIter != null) {
						if (tempIter.hasNext()) {
							E next = tempIter.next();
							checkForComodification();
							lastRet = count++;
							return next;
						} else {
							tempIter = null;
							priority--;
						}
					} else {
						// Get next iterator
						if (priority < 0) {
							checkForComodification();
							throw new NoSuchElementException();
						} else {
							if (queue[priority] == null) {
								tempIter = null;
								priority--;
							} else {
								tempIter = queue[priority].iterator();
								if (! tempIter.hasNext()) {
									tempIter = null;
									priority--;
								}
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
	
	public boolean contains(Object element)
	{
		for (int i = (queue.length - 1); i >= 0; i--) {
			List<E> list = queue[i];
			if (list != null && list.contains(element)) {
				return true;
			}
		}
		return false;
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

	@Override
	public boolean isEmpty() {
		return (size() == 0);
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size()];
		Iterator<E> i = iterator();
		int n = 0;
		while (i.hasNext()) {
			result[n] = i.next();
			n++;
		}
		return result;
	}

	@Override
	public <E> E[] toArray(E[] inputArray) {
		if (inputArray != null && inputArray.length >= size()) {
			Iterator<E> i = (Iterator<E>) iterator();
			int n = 0;
			while (i.hasNext()) {
				inputArray[n] = i.next();
				n++;
			}
			return inputArray;
		} else {
			E[] result = (E[]) Array.newInstance(inputArray.getClass().getComponentType(), size());
			Iterator<E> i = (Iterator<E>) iterator();
			int n = 0;
			while (i.hasNext()) {
				result[n] = i.next();
				n++;
			}
			return result;
		}
	}

	@Override
	public boolean remove(Object o) {
		boolean changed = false;
		Iterator<E> i = iterator();
		while (i.hasNext()) {
			E element = i.next();
			if (element.equals(o)) {
				i.remove();
				changed |= true;
			}
		}
		return changed;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			if (! contains(obj)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> col) {
		boolean changed = false;
		if (col != null) {
			for (E element : col) {
				changed |= add(element);
			}
		}

		return changed;
	}
	
	@Override
	public boolean removeAll(Collection<?> col) {
		boolean changed = false;
		
		if (col != null) {
			for (Object element : col) {
				changed |= remove(element);
			}
		}
		
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}

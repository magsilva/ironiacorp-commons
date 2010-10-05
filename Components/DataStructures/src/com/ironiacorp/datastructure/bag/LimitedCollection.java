package com.ironiacorp.datastructure.bag;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class LimitedCollection<T> implements Collection<T>
{
	private int maxSize;

	private LinkedList<T> collection;

	protected void evict(int size)
	{
		for (int i = 0; i < size; i++) {
			collection.removeFirst();
		}
	}

	public LimitedCollection(int maxSize)
	{
		if (maxSize < 0) {
			throw new IllegalArgumentException("Must be a non-negative number");
		}

		collection = new LinkedList<T>();
		this.maxSize = maxSize;
	}


	public boolean add(T e)
	{
		if (size() < maxSize) {
			evict(1);
		}
		return collection.add(e);
	}

	public boolean addAll(Collection<? extends T> c)
	{
		if (size() + c.size() <= maxSize) {
			evict(c.size());
		}
		return collection.addAll(c);
	}

	public void clear()
	{
		collection.clear();
	}

	public boolean contains(Object o)
	{
		return collection.contains(o);
	}

	public boolean containsAll(Collection<?> c)
	{
		return collection.containsAll(c);
	}

	public boolean isEmpty()
	{
		return collection.isEmpty();
	}

	public Iterator<T> iterator()
	{
		return collection.iterator();
	}

	public boolean remove(Object o)
	{
		return collection.remove(o);
	}

	public boolean removeAll(Collection<?> c)
	{
		return collection.removeAll(c);
	}

	public boolean retainAll(Collection<?> c)
	{
		return collection.retainAll(c);
	}

	public int size()
	{
		return collection.size();
	}

	public Object[] toArray()
	{
		return collection.toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		return collection.toArray(a);
	}
}

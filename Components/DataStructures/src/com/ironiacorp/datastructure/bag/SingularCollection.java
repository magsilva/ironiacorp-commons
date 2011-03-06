package com.ironiacorp.datastructure.bag;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingularCollection<T> implements Collection<T>
{
	private T obj;

	public boolean add(T e)
	{
		if ((obj == null && e != null) || (obj != null && ! obj.equals(e))) {
			obj = e;
			return true;
		}
		return false;
	}

	public boolean addAll(Collection<? extends T> c)
	{
		Iterator<? extends T> i = c.iterator();
		boolean result = false;
		while (i.hasNext()) {
			result = add(i.next());
		}
		return result;
	}

	public void clear()
	{
		obj = null;
	}

	public boolean contains(Object o)
	{
		if (obj == null) {
			return false;
		}

		if (obj == o || obj.equals(o)) {
			return true;
		}

		return false;
	}

	public boolean containsAll(Collection<?> c)
	{
		Iterator<?> i = c.iterator();
		while (i.hasNext()) {
			if (! contains(i.next())) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty()
	{
		if (obj == null) {
			return true;
		}
		return false;
	}

	public Iterator<T> iterator()
	{
		return new SingularCollectionIterator<T>();
	}

	public boolean remove(Object o)
	{
		if (obj == o || (obj != null && obj.equals(o))) {
			obj = null;
			return true;
		}
		return false;
	}

	public boolean removeAll(Collection<?> c)
	{
		Iterator<?> i = c.iterator();
		boolean hasChanged = false;
		while (i.hasNext()) {
			hasChanged |= remove(i.next());
		}
		return false;
	}

	public boolean retainAll(Collection<?> c)
	{
		if (! c.contains(obj)) {
			obj = null;
			return true;
		}
		return false;
	}

	public int size()
	{
		if (obj != null) {
			return 0;
		}
		return 1;
	}

	public Object[] toArray()
	{
		Object[] result = new Object[size()];
		if (obj != null) {
			result[0] = obj;
		}
		return result;
	}

	public <T> T[] toArray(T[] a)
	{
		if (a == null) {
			throw new NullPointerException();
		}

		T[] result =  (T[]) Array.newInstance(a.getClass().getComponentType(), size());
		if (obj != null) {
			result[0] = (T) obj;
		}

		return result;
	}

	private final class SingularCollectionIterator<T> implements Iterator<T>
	{
		private boolean hasNext;

		public SingularCollectionIterator()
		{
			if (isEmpty()) {
				hasNext = false;
			} else {
				hasNext = true;
			}
		}

		public boolean hasNext()
		{
			return hasNext;
		}

		public T next()
		{
			if (! hasNext()) {
				throw new NoSuchElementException();
			}
			return (T) obj;
		}

		public void remove()
		{
			if (! hasNext() && obj != null) {
				obj = null;
			} else {
				throw new IllegalStateException();
			}
		}
	}
}

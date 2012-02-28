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
 
Copyright (C) 2011 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
*/

package com.ironiacorp.datastructure.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class SingleElementSet<E> implements Set<E>
{
	private E element;
	
	private class SingleElementSetIterator implements Iterator<E> {
		private boolean unread = true;
		
		@Override
		public boolean hasNext() {
			if (element == null) {
				return false;
			} else {
				return unread;
			}
		}

		@Override
		public E next() {
			if (unread) {
				unread = false;
				if (element == null) {
					throw new NoSuchElementException();
				} else {
					return element;
				}
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			if (unread || element == null) {
				throw new IllegalStateException();
			}
			element = null;
		}
	};
	
	public SingleElementSet()
	{
		super();
	}

	public SingleElementSet(E e)
	{
		super();
		add(e);
	}
	
	@Override
	public int size() {
		return element == null ? 0 : 1;
	}

	@Override
	public boolean isEmpty() {
		return (element == null);
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		return o.equals(element);
	}

	@Override
	public Iterator<E> iterator() {
		return new SingleElementSetIterator();
	}

	@Override
	public Object[] toArray() {
		Object[] array = (isEmpty() ? new Object[0] : new Object[1]);
		if (! isEmpty()) {
			array[0] = element;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		int size = (isEmpty() ? 0 : 1);
		if (a.length < size) {
			a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		}
		if (size == 1) {
			a[0] = (T) element;
			if (a.length > 1) {
				a[1] = null;
			}
		}
		return a;
	}


	@Override
	public boolean add(E e) {
		if (element == null) {
			element = e;
			return true;
		}
		if (element == e) {
			return false;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		if (element == o) {
			clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c.size() == 1) {
			return c.contains(element);
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		while (i.hasNext()) {
			Object object = i.next();
			if (object != null) {
				if (object.equals(element)) {
					return false;
				}
			}
		}
		
		clear();
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		while (i.hasNext()) {
			Object object = i.next();
			if (object != null) {
				if (object.equals(element)) {
					clear();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void clear() {
		element = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + ((element == null) ? 0 : element.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingleElementSet other = (SingleElementSet) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}
}

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Data structure that behaves like a set, but that allows the recovery
 * of values by key. The key is either the object identity (as of its
 * hashCode()) or a method defined at the initialization of the structure.
 * 
 * This structure has the goal of saving some memory for applications that
 * uses data structures with set behaviour, but whose object's identity
 * is defined for just some of its properties. So, instead of having two
 * objects that are the same (as established the equals() contract), the
 * application can use just one object (replacing one of the instances
 * by the one recovered by the get() method). 
 * 
 * @param <K> The type of the key (if the default constructor is defined,
 * this should be an String (the default identity is defined as
 * Integer.toHexString(object.hashValue())).
 * @param <V> The type of the value stored in the set.
 */
public class IdentityMethodSet<K, V>
{
	private Map<K, V> map;
	
	private String identityMethodName;
	
	public IdentityMethodSet()
	{
		this(null);
	}
	
	public IdentityMethodSet(String identityMethodName)
	{
		if (identityMethodName != null) {
			this.identityMethodName = identityMethodName;
		}
		map = new TreeMap<K, V>();
	}
	
	protected K getDefaultIdentity(V object)
	{
		return (K) Integer.toHexString(object.hashCode());
	}
	
	public K getIdentity(V object)
	{
		if (identityMethodName == null) {
			return getDefaultIdentity(object);
		}
		
		try {
			Method method = object.getClass().getMethod(identityMethodName);
			Object result = method.invoke(object);
			return (K) result;
		} catch (NoSuchMethodException e) {
			throw new UnsupportedOperationException("Identity method not supported.");
		} catch (Exception e) {
			throw new UnsupportedOperationException("Identity method could not be executed.");
		}
	}
	
	public V add(V object)
	{
		if (object == null) {
			throw new IllegalArgumentException(new NullPointerException("Specified element is null (and such element is not permitted within this data structure"));
		}
		
		K key = getIdentity(object);
		if (! map.containsKey(key)) {
			map.put(key, object);
		}
		return map.get(key);
	}

	public Collection<? extends V> addAll(Collection<? extends V> objects)
	{
		if (objects == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		List<V> result = new ArrayList<V>();
		Iterator<? extends V> i = objects.iterator();
		while (i.hasNext()) {
			V object = i.next();
			object = add(object);
			result.add(object);
		}
		
		return result;
	}
	
	public boolean contains(V object)
	{
		if (object == null) {
			return false;
		}

		K key = getIdentity(object);
		return map.containsKey(key);
	}

	public boolean containsAll(Collection<? extends V> objects)
	{
		Iterator<? extends V> i = objects.iterator();
		while (i.hasNext()) {
			V object = i.next();
			if (! contains(object)) {
				return false;
			}
		}
		return true;
	}

	public V getByKey(K key)
	{
		if (key == null) {
			throw new IllegalArgumentException(new NullPointerException("Invalid key for an element of the set"));
		}
		
		return map.get(key);
	}

	public V getByValue(V object)
	{
		if (object == null) {
			throw new IllegalArgumentException(new NullPointerException("Null object are not allowed"));
		}
		
		K key = getIdentity(object);
		return map.get(key);
	}
	
	public V get(V object)
	{
		return getByValue(object);
	}
	
	public Iterator<V> iterator()
	{
		return map.values().iterator();
	}

	public V remove(V object)
	{
		return removeByValue(object);
	}
	
	public V removeByValue(V object)
	{
		if (object == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		K key = getIdentity(object);
		if (map.containsKey(key)) {
			return map.remove(key);
		}
		
		return null;
	}

	public V removeByKey(K key)
	{
		if (key == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		if (map.containsKey(key)) {
			return map.remove(key);
		}
		
		return null;
	}
	
	public void removeAll(Collection<?> objects)
	{
		map.clear();
	}

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public int size()
	{
		return map.size();
	}
}
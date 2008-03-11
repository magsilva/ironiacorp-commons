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

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons;

import java.util.Arrays;
import java.util.Comparator;


/**
 * Some basic array utils.
 */
public final class ArrayUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private ArrayUtil()
	{
	}
	
	/**
	 * Check if an array has the given object. If the object is 'null', it will
	 * always return False
	 * 
	 * @param array The array we will search into.
	 * @param o The object to be found.
	 * 
	 * @return True if the object was found in the array, False otherwise.
	 */
	public static boolean has(Object[] array, Object o)
	{
		if (array == null) {
			return false;
		}
		
		for (Object temp : array) {
			if (temp == null) {
				if (o == null) {
					return true;
				}
			} else {
				if (temp.equals(o)) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Create a copy of an array. It will not do a deep copy (the primitive values
	 * and object references are copied to the targed array, but the objects refereed
	 * by both arrays will be the same).
	 * 
	 * @param array Array to be copied.
	 * 
	 * @return Duplicated array.
	 */
	public static Object[] dup(Object[] array)
	{
		if (array == null) {
			return null;
		}
		
		Object[] dupArray = new Object[array.length];
		System.arraycopy(array, 0, dupArray, 0, array.length);
		return dupArray;
	}
	
	public static boolean equalIgnoreOrder(Object[] array1, Object[] array2)
	{
		final class HashComparator<T> implements Comparator<T>
		{
			/**
			 * Compares its two arguments for ordering. Returns a negative integer, zero,
			 * or a positive integer as the first argument is less than, equal to, or
			 * greater than the second.
			 * 
			 * We use this comparator because it does not depends upon any specific object
			 * property. Any Java object has an hashCode().
			 * 
			 * Note: this comparator imposes orderings that are inconsistent with equals.
			 */
			public int compare(T o1, T o2)
			{
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 == null && o2 != null) {
					return -1;
				}
				if (o1 != null && o2 == null) {
					return 1;
				}
				
				int hash1 = o1.hashCode();
				int hash2 = o2.hashCode();
				return (hash1 - hash2);
			}
		}
		
		if (array1 == null && array2 != null) {
			return false;
		}
		if (array1 != null && array2 == null) {
			return false;
		}
		if (array1 == null && array2 == null) {
			return true;
		}
		
		if (array1.length != array2.length) {
			return false;
		}
		
		Object[] sortedArray1 = dup(array1);
		Object[] sortedArray2 = dup(array2);
		Arrays.sort(sortedArray1, new HashComparator<Object>());
		Arrays.sort(sortedArray2, new HashComparator<Object>());
		
		return Arrays.equals(sortedArray1, sortedArray2);
	}

	
	/**
	 * Find an object in the array that is an instance of the given targetClass.
	 * 
	 * @param array Array we must look into.
	 * @param targetClass Class that we must match.
	 * 
	 * @return The object (if found) or null otherwise.
	 */
	public static Object find(Object[] array, Class targetClass)
	{
		if (array == null) {
			return null;
		}
		
		for (Object o : array) {
			try {
				if (o.getClass().equals(targetClass)) {
					return o;
				}
			} catch (NullPointerException e) {
			}
		}
		
		return null;
	}

	/**
	 * Find the string index within a given array.
	 * 
	 * @param array Array to be searched.
	 * @param str String to be found.
	 * 
	 * @return The index for the string within the array or -1 if the
	 * string couldn't be found.
	 */
	public static int find(String[] array, String str)
	{
		if (array == null) {
			return -1;
		}
		
		int i = 0;
		for (i = 0; i < array.length && ! array[i].equals(str); i++);

		if (i == array.length) {
			i = -1;
		}
		
		return i;
	}
	
	/**
	 * Convert a Array to a single String. It will use a new line character ("\n")
	 * as separator.
	 * 
	 * @param arg The array to be converted.
	 * 
	 * @return The string.
	 */
	public static String toString(Object[] arg)
	{
		return ArrayUtil.toString(arg, "\n");
	}
	
	/**
	 * Convert a Array to a single String.
	 * 
	 * @param arg The array to be converted
	 * @param separator The string to be used to separate each array item.
	 * 
	 * @return The string.
	 */
	public static String toString(Object[] arg, String separator)
	{
		if (separator == null) {
			throw new IllegalArgumentException("The separator string cannot be null");
		}
		
		StringBuffer sb = new StringBuffer();
		for (Object o : arg) {
			if (o != null) {
				sb.append(o);
				sb.append(separator);
			}
		}
		if (sb.lastIndexOf(separator) != -1) {
			sb.replace(sb.lastIndexOf(separator), sb.lastIndexOf(separator) + separator.length(), "");
		}
		return sb.toString();
	}
	
	/**
	 * Remove null values from an array.
	 * 
	 * @param <T> Array type.
	 * @param array Array to be cleaned.
	 * 
	 * @return Cleant array.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] clean(T[] array)
	{
		int nonNullCount = 0;
		int i = 0;
		Object[] result = null;
		
		if (array == null) {
			return null;
		}
		
		for (Object obj : array) {
			if (obj != null) {
				nonNullCount++;
			}
		}
		
		result = (T[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), nonNullCount);
		for (Object obj : array) {
			if (obj != null) {
				result[i] = obj;
				i++;
			}
		}
			
		return (T[])result;
	}
	
	/**
	 * Shortcut to create an array of objects.
	 * 
	 * @param <T> The type of the objects to be stored in the array
	 * @param elements The objects to save into the array.
	 * @return The array.
	 */
    public static <T> T[] array(T... elements)
    {
    	return elements;
    }
}
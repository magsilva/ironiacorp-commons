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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;



public final class ObjectUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private ObjectUtil()
	{
	}
	
	/**
	 * Check if a given object is empty. Emptiness is defined as follows:
	 * - if null, it's empty.
	 * - if it's a String, return StringUtil.isEmpty();
	 * - if it's a Number (Byte, Integer, Long) and equals to 0, then it's
	 * empty.
	 * - if it's a Boolean, if false it's empty.
	 * - Otherwise, it's not empty.
	 * 
	 * @param obj The object to be checked.
	 * 
	 * @return True if empty, false otherwise.
	 */
	public static boolean isEmpty(Object obj)
	{
		if (obj == null) {
			return true;
		}
		
		if (obj instanceof String) {
			return StringUtil.isEmpty((String) obj);
		}
		
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return (n.longValue() == 0L);
		}
		
		if (obj instanceof Boolean) {
			return ! (Boolean) obj;
		}
		
		return false;
	}
	
	/**
	 * Copy the values of one object to another object.
	 * 
	 * @param src The object the data will be copied from.
	 * @param dest The object the data will be copied to.
	 */
	public static void sync(Object src, Object dest)
	{
		Class<?> srcClass = src.getClass();
		Class<?> destClass = dest.getClass();
		if (srcClass != destClass) {
			throw new IllegalArgumentException("Objects are incompatible");
		}
		
		Map<String, Method> srcData = JavaBeanUtil.mapBeanPropertiesToGetMethods(srcClass);
		Map<String, Method> destData = JavaBeanUtil.mapBeanPropertiesToSetMethods(destClass);
		
		for (String key : srcData.keySet()) {
			Method srcGetMethod = srcData.get(key);
			Method destSetMethod = destData.get(key);
			Object args[] = new Object[1]; 
			try {
				args[0] = srcGetMethod.invoke(src, (Object[]) null);
				destSetMethod.invoke(dest, args);
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}
}

/*
 * Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.introspector;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.ironiacorp.datastructure.array.ArrayUtil;
import com.ironiacorp.string.StringUtil;

public class ObjectIntrospector
{
	public static final Pattern SETTER_PATTERN = Pattern.compile("set([A-Z][A-Za-z0-9]*)$");
	
	public static final Pattern GETTER_PATTERN = Pattern.compile("(get|is|has)([A-Z][A-Za-z0-9]*)$");
	/*
	 matcher = GETTER_PATTERN.matcher(method.getName());
     if (matcher.matches() && method.getParameterTypes().length == 0) {
         String raw = matcher.group(2);
         return raw.substring(0, 1).toLowerCase() + raw.substring(1);
     }
	*/
	
	/**
	 * Prefix for methods that read a JavaBean property.
	 */
	public static String GETTER = "get";
	
	/**
	 * Prefix for methods that write a JavaBean property.
	 */
	public static String SETTER = "set";

	/**
	 * Suffix for class attributes that contains the name of a class property.
	 */
	public static String FIELD_IDENTIFIER = "_FIELD";
	
	/**
	 * Properties to be ignored (actually default Java object's properties.
	 */
	public static String[] IGNORED_PROPERTIES = {
		"class"
	};

	public String getPropertyNameFromMethod(Method m)
	{
		if (! m.getName().startsWith(GETTER)) {
			throw new IllegalArgumentException("Method is not a property getter");
		}
		
		String property = m.getName();
		property = property.substring(GETTER.length());
		property = property.substring(0, 1).toLowerCase() + property.substring(1);
	
		return property;
	}

	
	public String[] getPropertiesNameFromMethod()
	{
		ArrayList<String> props = new ArrayList<String>();
		Object bean = new Object();
		Method[] methods = bean.getClass().getMethods();
		
		for (Method m : methods) {
			try {
				props.add(getPropertyNameFromMethod(m));
			} catch (IllegalArgumentException iae) {
			}
		}
		return props.toArray(new String[0]);
	}
	
	
	/**
	 * Map a JavaBean to a Map. The map's keys are the JavaBeans' properties'
	 * names (Strings) and the maps' values the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public Map<String, Object> mapBean(Object bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Method[] methods = bean.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String temp = key.substring(GETTER.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);

				// Actually, we should be using the getDefaultPropertiesName() method
				// to get the properties to be ignored, but we are faster this way.
				if (! ArrayUtil.has(IGNORED_PROPERTIES, key)) {
					try {
						Object value = m.invoke(bean, (Object [])null); 
						map.put(key, value);
					} catch (IllegalAccessException iae) {
					} catch (InvocationTargetException ite) {
					}
				}
			}
		}
		return map;
	}

	public Map<String, Method> mapBeanPropertiesToGetMethods(Object bean)
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		
		Method[] methods = bean.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String temp = key.substring(GETTER.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);
				map.put(key, m);
			}
		}
		return map;
	}

	public Map<String, Method> mapBeanPropertiesToSetMethods(Object bean)
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		
		Method[] methods = bean.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(SETTER)) {
				String key = m.getName();
				String temp = key.substring(SETTER.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);
				map.put(key, m);
			}
		}
		return map;
	}

	
	public String toString(File dir, File filename)
	{
		return toString(dir.getName(), filename.getName());
	}
	
	public String toString(String dir, String filename)
	{
		int baseNameSize = dir.length();
		String className = dir.substring(baseNameSize);
		className = className.replaceAll(File.separator, ReflectionUtil.PACKAGE_DELIMITER);
		if (className.endsWith(ReflectionUtil.CLASS_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.CLASS_FILE_EXTENSION));
		}
		if (className.endsWith(ReflectionUtil.JAVA_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.JAVA_FILE_EXTENSION));
		}
		
		return className;
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
	public void sync(Object src, Object dest)
	{
		Class<?> srcClass = src.getClass();
		Class<?> destClass = dest.getClass();
		if (srcClass != destClass) {
			throw new IllegalArgumentException("Objects are incompatible");
		}
		
		Map<String, Method> srcData = mapBeanPropertiesToGetMethods(src);
		Map<String, Method> destData = mapBeanPropertiesToSetMethods(dest);
		
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

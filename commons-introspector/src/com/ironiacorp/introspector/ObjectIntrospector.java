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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.ironiacorp.datastructure.array.ArrayUtil;
import com.ironiacorp.introspector.MethodIntrospector.BeanMethodType;
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
	 * Suffix for class attributes that contains the name of a class property.
	 */
	public static final String FIELD_IDENTIFIER = "_FIELD";
	
	/**
	 * Properties to be ignored (actually default Java object's properties.
	 */
	public static final String[] IGNORED_PROPERTIES = {
		"class"
	};
	
	private Object object;

	
	
	
	public Object getObject()
	{
		return object;
	}


	public void setObject(Object object)
	{
		this.object = object;
	}


	public String[] getPropertiesNameFromMethod()
	{
		ArrayList<String> props = new ArrayList<String>();
		Object bean = new Object();
		Method[] methods = bean.getClass().getMethods();
		MethodIntrospector mi = new MethodIntrospector();
		
		for (Method m : methods) {
			mi.setMethod(m);
			try {
				String property = mi.getPropertyName();
				if (property != null) {
					props.add(property);
				}
			} catch (IllegalArgumentException iae) {
			}
		}
		return props.toArray(new String[0]);
	}

	
	/**
	 * Get property value of the object.
	 * 
	 * @param name Property name.
	 * @return Property value.
	 * @throws IllegalArgumentException if the object have not got the property.
	 */
	public Object getProperty(String name)
	{
		Method method = getGetter(name);
		try {
			Object result = method.invoke(object);
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Map a JavaBean to a Map. The map's keys are the JavaBeans' properties'
	 * names (Strings) and the maps' values the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public Map<String, Object> map()
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		Method[] methods = object.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(BeanMethodType.GETTER.prefix)) {
				String key = m.getName();
				String temp = key.substring(BeanMethodType.GETTER.prefix.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);

				// Actually, we should be using the getDefaultPropertiesName() method
				// to get the properties to be ignored, but we are faster this way.
				if (! ArrayUtil.has(IGNORED_PROPERTIES, key)) {
					try {
						Object value = m.invoke(object, (Object [])null); 
						map.put(key, value);
					} catch (IllegalAccessException iae) {
					} catch (InvocationTargetException ite) {
					}
				}
			}
		}
		return map;

	}

	
	public Method getGetter(String property)
	{
		try {
			Method method = object.getClass().getMethod(BeanMethodType.GETTER.prefix + property.substring(0, 1).toUpperCase() + property.substring(1));
			return method;
		} catch (Exception e) {
			return null;
		}
	}

	public Method getSetter(String property)
	{
		try {
			Method method = object.getClass().getMethod(BeanMethodType.SETTER.prefix + property.substring(0, 1).toUpperCase() + property.substring(1));
			return method;
		} catch (Exception e) {
			return null;
		}
	}

	
	public Map<String, Method> mapPropertiesToGetMethods()
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		Method[] methods = object.getClass().getMethods();
		MethodIntrospector mi = new MethodIntrospector();
		for (Method m : methods) {
			mi.setMethod(m);
			String key = mi.getPropertyName(BeanMethodType.GETTER);
			if (key != null) {
				map.put(key, m);
			}
		}
		return map;
	}

	public Map<String, Method> mapPropertiesToSetMethods()
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		Method[] methods = object.getClass().getMethods();
		MethodIntrospector mi = new MethodIntrospector();
		for (Method m : methods) {
			mi.setMethod(m);
			String key = mi.getPropertyName(BeanMethodType.SETTER);
			if (key != null) {
				map.put(key, m);
			}
		}
		return map;
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
	public void sync(Object dest)
	{
		Map<String, Method> srcData;
		Map<String, Method> destData;
		ObjectIntrospector oi;
		Class<?> destClass = dest.getClass();
		
		if (object.getClass() != destClass) {
			throw new IllegalArgumentException("Objects are incompatible");
		}
		
		oi = new ObjectIntrospector();
		oi.setObject(dest);
		srcData = mapPropertiesToGetMethods();
		destData = oi.mapPropertiesToSetMethods();
		
		for (String key : srcData.keySet()) {
			Method srcGetMethod = srcData.get(key);
			Method destSetMethod = destData.get(key);
			Object args[] = new Object[1]; 
			try {
				args[0] = srcGetMethod.invoke(object, (Object[]) null);
				destSetMethod.invoke(dest, args);
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}
}

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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.ironiacorp.commons.persistence.DbAnnotations;
import com.ironiacorp.commons.persistence.Property;



/**
 * Utility class for JavaBeans.
 */
public final class JavaBeanUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private JavaBeanUtil()
	{
	}
	
	private static Pattern SETTER_PATTERN = Pattern.compile("set([A-Z][A-Za-z0-9]*)$");
	private static Pattern GETTER_PATTERN = Pattern.compile("(get|is|has)([A-Z][A-Za-z0-9]*)$");
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

	public static String getPropertyNameFromMethod(Method m)
	{
		if (! m.getName().startsWith(GETTER)) {
			throw new IllegalArgumentException("Method is not a property getter");
		}
		
		String property = m.getName();
		property = property.substring(GETTER.length());
		property = property.substring(0, 1).toLowerCase() + property.substring(1);
	
		return property;
	}

	
	public static String[] getPropertiesNameFromMethod()
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
	public static Map mapBean(Object bean)
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
	
	/**
	 * Map a JavaBean to a Map. The map's keys are the fields set at the JavaBean
	 * (public static final Strings) whose names matches the GETTERs names. The map's
	 * values are the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public static Map<String, Object> mapBeanUsingFields(Object bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> fieldsMap = new HashMap<String, String>();
		Field[] fields = bean.getClass().getDeclaredFields();
		Method[] methods = bean.getClass().getMethods();
		
		for (Field f : fields) {
			String key = null;
			String value = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(DbAnnotations.PROPERTY_ANNOTATION)) {
				Property ann = (Property)f.getAnnotation(DbAnnotations.PROPERTY_ANNOTATION);
				key = ann.value();
				try {
					value = (String) f.get(bean);					
				} catch (IllegalAccessException iae) {
				}
			// Otherwise, stick with that old-fashioned method that relays on Java reflection features.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							try {
								key = fieldName.substring(0, fieldName.length() - FIELD_IDENTIFIER.length());
								value = (String) f.get(bean);
							} catch (IllegalAccessException iae) {
							}
						}
					}
				}
			}
			if (key != null) {
				fieldsMap.put(key, value);
			}
		}
		
		for (Method m : methods) {
			String key = null;
			Object value = null;
			if (m.getName().startsWith(GETTER)) {
				key = m.getName();
				key = key.substring(GETTER.length());
				value = StringUtil.findSimilar(fieldsMap.keySet(), key);
				if (value != null) {
					key = fieldsMap.get(value);
					try {
						value = m.invoke(bean, (Object [])null); 
					} catch (IllegalAccessException iae) {
					} catch (InvocationTargetException ite) {
					}
				}
			}
			map.put(key, value);
		}
		return map;
	}
	
	
	public static Map<String, Method> mapBeanPropertiesToSetMethods(Class clazz)
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		Field[] fields = clazz.getDeclaredFields();
		HashMap<String, String> propertyFields = new HashMap<String, String>();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Field f : fields) {
			String key = f.getName();
			String value = null;
			Property ann = null;

			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(Property.class)) {
				ann = (Property)f.getAnnotation(DbAnnotations.PROPERTY_ANNOTATION);
				value = ann.value();
			// Otherwise, we try to guess.
			} else {
				if (key.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							value = key.substring(0, key.length() - FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			if (value != null) {
				propertyFields.put(key, value);
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(SETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(SETTER.length());
				field = StringUtil.findSimilar(propertyFields.keySet(), key);
				if (field != null) {
					map.put(propertyFields.get(field), m);
				}
			}
		}
		return map;
	}

	public static Map<String, Method> mapBeanPropertiesToGetMethods(Class clazz)
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		Field[] fields = clazz.getDeclaredFields();
		HashMap<String, String> propertyFields = new HashMap<String, String>();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Field f : fields) {
			String key = f.getName();
			String value = null;
			Property ann = null;

			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(Property.class)) {
				ann = (Property)f.getAnnotation(DbAnnotations.PROPERTY_ANNOTATION);
				value = ann.value();
			// Otherwise, we try to guess.
			} else {
				if (key.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							value = key.substring(0, key.length() - FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			if (value != null) {
				propertyFields.put(key, value);
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(GETTER.length());
				field = StringUtil.findSimilar(propertyFields.keySet(), key);
				if (field != null) {
					map.put(propertyFields.get(field), m);
				}
			}
		}
		return map;
	}

	
	/*
	public static Map<String, Method> mapBeanPropertiesToMethods(Class clazz)
	{
		HashMap<String, Method> map = new HashMap<String, Method>();
		Field[] fields = clazz.getDeclaredFields();
		TreeSet<String> propertyFields = new TreeSet<String>();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Field f : fields) {
			String key = null;
			Property ann = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(Property.class)) {
				// Property ann = (Property)ArrayUtil.find(f.getDeclaredAnnotations(), Property.class);
				ann = (Property)f.getAnnotation(DbAnnotations.PROPERTY_ANNOTATION);
				key = ann.value();
			// Otherwise, we try to guess.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							key = fieldName.substring(0, fieldName.length() - FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			if (key != null) {
				propertyFields.add(key);
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(GETTER.length());
				field = StringUtil.findSimilar(propertyFields, key);
				if (field != null) {
					map.put(field, m);
				}
			}
		}
		return map;
	}
	*/
	
	public static String[] getBeanProperties(Class clazz)
	{
		Field[] fields = clazz.getDeclaredFields();
		ArrayList<String> propertyFields = new ArrayList<String>();
		
		for (Field f : fields) {
			String key = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(Property.class)) {
				Property ann = (Property)f.getAnnotation(DbAnnotations.PROPERTY_ANNOTATION);
				key = ann.value();
			// Otherwise, we try to guess.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							key = fieldName.substring(0, fieldName.length() - FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			propertyFields.add(key);
		}
		
		return propertyFields.toArray(new String[0]);
	}

}

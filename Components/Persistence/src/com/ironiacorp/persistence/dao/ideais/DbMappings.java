package com.ironiacorp.persistence.dao.ideais;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ironiacorp.introspector.ObjectIntrospector;
import com.ironiacorp.string.StringUtil;

public class DbMappings
{
	/**
	 * Map a JavaBean to a Map. The map's keys are the fields set at the JavaBean
	 * (public static final Strings) whose names matches the GETTERs names. The map's
	 * values are the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public static Map<String, Object> mapBeanUsingFields(Class<? extends Annotation> annotation, Object bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> fieldsMap = new HashMap<String, String>();
		Field[] fields = bean.getClass().getDeclaredFields();
		Method[] methods = bean.getClass().getMethods();
		
		for (Field f : fields) {
			String key = null;
			String value = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(annotation)) {
				Property ann = (Property) f.getAnnotation(annotation);
				key = ann.value();
				try {
					value = (String) f.get(bean);					
				} catch (IllegalAccessException iae) {
				}
			// Otherwise, stick with that old-fashioned method that relays on Java reflection features.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(ObjectIntrospector.FIELD_IDENTIFIER)) {
					Class<?> type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							try {
								key = fieldName.substring(0, fieldName.length() - ObjectIntrospector.FIELD_IDENTIFIER.length());
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
			if (m.getName().startsWith(ObjectIntrospector.GETTER)) {
				key = m.getName();
				key = key.substring(ObjectIntrospector.GETTER.length());
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
	
	
	public static Map<String, Method> mapBeanPropertiesToSetMethods(Class<?> clazz)
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
				if (key.endsWith(ObjectIntrospector.FIELD_IDENTIFIER)) {
					Class<?> type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							value = key.substring(0, key.length() - ObjectIntrospector.FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			if (value != null) {
				propertyFields.put(key, value);
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(ObjectIntrospector.SETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(ObjectIntrospector.SETTER.length());
				field = StringUtil.findSimilar(propertyFields.keySet(), key);
				if (field != null) {
					map.put(propertyFields.get(field), m);
				}
			}
		}
		return map;
	}

	public static Map<String, Method> mapBeanPropertiesToGetMethods(Class<?> clazz)
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
				if (key.endsWith(ObjectIntrospector.FIELD_IDENTIFIER)) {
					Class<?> type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							value = key.substring(0, key.length() - ObjectIntrospector.FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			if (value != null) {
				propertyFields.put(key, value);
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(ObjectIntrospector.GETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(ObjectIntrospector.GETTER.length());
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
	
	public static String[] getBeanProperties(Class<?> clazz, Class<? extends Annotation> annotation)
	{
		Field[] fields = clazz.getDeclaredFields();
		ArrayList<String> propertyFields = new ArrayList<String>();
		
		for (Field f : fields) {
			String key = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(Property.class)) {
				Property ann = (Property)f.getAnnotation(annotation);
				key = ann.value();
			// Otherwise, we try to guess.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(ObjectIntrospector.FIELD_IDENTIFIER)) {
					Class<?> type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							key = fieldName.substring(0, fieldName.length() - ObjectIntrospector.FIELD_IDENTIFIER.length());
						}
					}
				}
			}
			propertyFields.add(key);
		}
		
		return propertyFields.toArray(new String[0]);
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
		
		Map<String, Method> srcData = mapBeanPropertiesToGetMethods(srcClass);
		Map<String, Method> destData = mapBeanPropertiesToSetMethods(destClass);
		
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

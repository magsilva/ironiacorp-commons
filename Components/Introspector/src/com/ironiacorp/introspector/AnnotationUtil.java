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
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
*/

package com.ironiacorp.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities to process annotations at runtime.
 */
public final class AnnotationUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private AnnotationUtil()
	{
	}
	
	/**
	 * Java's annotation default value.
	 */
	public static String DEFAULT_PROPERTY = "value";

	/**
	 * Get the value set for an annotated class with a single element.
	 * 
	 * @param obj The annotated object's class.
	 * @param ann The annotation we need to read. 
	 * 
	 * @return The annotation's value.
	 */
	public static String getAnnotationValue(Class<? extends Object> clazz, Class<? extends Annotation> ann)
	{
		return getAnnotationValue(clazz, ann, DEFAULT_PROPERTY);
	}
	
	
	/**
	 * Get the value set for an annotated class.
	 * 
	 * @param obj The annotated object's class.
	 * @param ann The annotation we need to read.
	 * @param property The annotation's property we need to read.
	 *  
	 * @return The annotation's value.
	 */	
	public static String getAnnotationValue(Class<? extends Object> clazz, Class<? extends Annotation> ann, String property)
	{
		Annotation a = clazz.getAnnotation(ann);
		String value = null;

		if (a == null) {
			throw new IllegalArgumentException();
		}

		try {
			Method m  = ann.getDeclaredMethod(property, (Class[])null);
			value = (String) m.invoke(a, (Object [])null);
		} catch (NoSuchMethodException nsme) {
		} catch (IllegalAccessException iae) {
		} catch (InvocationTargetException ite) {
		}
		
		return value;
	}
	

	/**
	 * Get the value of an annotated field with a single element. It's kidda odd,
	 * but you cannot read the annotation of an Field using the generic Class
	 * method.
	 * 
	 * @param field The annotated object.
	 * @param ann The annotation we need to read.
	 *  
	 * @return The annotation's value for the given object.
	 */	
	public static String getAnnotationValue(Field field, Class<? extends Annotation> ann)
	{
		return getAnnotationValue(field, ann, DEFAULT_PROPERTY);
	}

	
	/**
	 * Get the value set for an annotated field.  It's kidda odd,
	 * but you cannot read the annotation of an Field using the generic Class
	 * method.
	 * 
	 * @param field The annotated object.
	 * @param ann The annotation we need to read.
	 * @param property The annotation's property we need to read.
	 *  
	 * @return The annotation's value for the given object.
	 */	
	public static String getAnnotationValue(Field field, Class<? extends Annotation> ann, String property)
	{
		Annotation a = field.getAnnotation(ann);
		String value = null;

		if (a == null) {
			throw new IllegalArgumentException();
		}

		try {
			Method m  = ann.getDeclaredMethod(property, (Class[])null);
			value = (String) m.invoke(a, (Object [])null);
		} catch (NoSuchMethodException nsme) {
		} catch (IllegalAccessException iae) {
		} catch (InvocationTargetException ite) {
		}
		
		return value;
	}

	

	/**
	 * Check if the class has annotations.
	 * 
	 * @param clazz Class to be checked.
	 * @return True if the class is annotated, False otherwise.
	 */
	public static boolean hasAnnotations(Class<?> clazz)
	{
		if (clazz.getAnnotations().length != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the class has annotations.
	 * 
	 * @param clazz Class to be checked.
	 * @param ann Annotation to be checked.
	 * 
	 * @return True if the class is annotated with the given annotation, False otherwise.
	 */
	public static boolean hasAnnotations(Class<? extends Object> clazz, Class<? extends Annotation> ann)
	{
		if (clazz.getAnnotation(ann) != null) {
			return true;
		}
		return false;
	}

		
	/**
	 * Check if the field has annotations.
	 * 
	 * @param field Field to be checked.
	 * @return True if the field is annotated, False otherwise.
	 */
	public static boolean hasAnnotations(Field field)
	{
		if (field.getAnnotations().length != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the field has annotations.
	 * 
	 * @param field Field to be checked.
	 * @param ann Annotation to be checked.
	 * 
	 * @return True if the field is annotated with the given annotation, False otherwise.
	 */
	public static boolean hasAnnotations(Field field, Class<? extends Annotation> ann)
	{
		if (field.getAnnotation(ann) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the bean properties (fields) that are annotated.
	 * 
	 * @param clazz Class to be inspected.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public static Field[] getAnnotatedFields(Class<?> clazz)
	{
		return getAnnotatedFields(clazz, null);	
	}
	
	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param clazz Class to be inspected.
	 * @param annClass The annotation.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public static Field[] getAnnotatedFields(Class<?> clazz, Class<?> annClass)
	{
		List<Field> properties = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field f : fields) {
			Annotation[] annotations = f.getAnnotations();
			for (Annotation ann : annotations) {
				if (ann.annotationType().equals(annClass) || annClass == null) {
					properties.add(f);
				}
			}
		}
		
		return properties.toArray(new Field[0]);
	}
	
	
	/**
	 * Get the bean properties (fields) that are annotated.
	 * 
	 * @param clazz Class to be inspected.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public static Method[] getAnnotatedMethods(Class<?> clazz)
	{
		return getAnnotatedMethods(clazz, null);	
	}
	
	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param clazz Class to be inspected.
	 * @param annClass The annotation.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public static Method[] getAnnotatedMethods(Class<?> clazz, Class<?> annClass)
	{
		List<Method> properties = new ArrayList<Method>();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Method m : methods) {
			Annotation[] annotations = m.getAnnotations();
			for (Annotation ann : annotations) {
				if (ann.annotationType().equals(annClass) || annClass == null) {
					properties.add(m);
				}
			}
		}
		
		return properties.toArray(new Method[0]);
	}
}
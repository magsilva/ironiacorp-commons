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

package com.ironiacorp.annotation;

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
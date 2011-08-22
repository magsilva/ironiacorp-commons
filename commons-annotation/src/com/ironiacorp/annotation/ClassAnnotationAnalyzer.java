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
public final class ClassAnnotationAnalyzer implements AnnotationAnalyzer
{
	private Class<?> clazz;
	
	public Class<?> getClas() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public  String getAnnotationValue(Class<? extends Annotation> ann)
	{
		return getAnnotationValue(ann, DEFAULT_PROPERTY);
	}
	
	
	@Override
	public String getAnnotationValue(Class<? extends Annotation> ann, String property)
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

	@Override
	public boolean hasAnnotations()
	{
		if (clazz.getAnnotations().length != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAnnotations(Class<? extends Annotation> ann)
	{
		if (clazz.getAnnotation(ann) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Get the bean properties (fields) that are annotated.
	 * 
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public Field[] getAnnotatedFields()
	{
		return getAnnotatedFields(null);	
	}
	
	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param annClass The annotation.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public Field[] getAnnotatedFields(Class<? extends Annotation> annClass)
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
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public Method[] getAnnotatedMethods()
	{
		return getAnnotatedMethods(null);	
	}
	
	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param annClass The annotation.
	 * @return The fields that are annotated. If no annotated fields were found, it
	 * returns an empty array.
	 */
	public Method[] getAnnotatedMethods(Class<? extends Annotation> annClass)
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
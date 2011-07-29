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

/**
 * Utilities to process annotations at runtime.
 */
public final class FieldAnnotationAnalyzer implements AnnotationAnalyzer
{
	private Field field;
	
	public Field getField()
	{
		return field;
	}


	public void setField(Field field)
	{
		this.field = field;
	}
	
	@Override
	public  String getAnnotationValue(Class<? extends Annotation> ann)
	{
		return getAnnotationValue(ann, DEFAULT_PROPERTY);
	}

	@Override
	public String getAnnotationValue(Class<? extends Annotation> ann, String property)
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

	@Override
	public boolean hasAnnotations()
	{
		if (field.getAnnotations().length != 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasAnnotations(Class<? extends Annotation> ann)
	{
		if (field.getAnnotation(ann) != null) {
			return true;
		}
		return false;
	}
}
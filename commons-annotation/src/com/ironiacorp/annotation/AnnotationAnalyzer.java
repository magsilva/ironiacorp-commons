/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

public interface AnnotationAnalyzer
{
	/**
	 * Java's annotation default value.
	 */
	public static final String DEFAULT_PROPERTY = "value";

	/**
	 * Get the value set for an annotated class with a single element.
	 * 
	 * @param obj The annotated object's class.
	 * @param ann The annotation we need to read. 
	 * 
	 * @return The annotation's value.
	 */
	String getAnnotationValue(Class<? extends Annotation> ann);
	
	
	/**
	 * Get the value set for an annotated class.
	 * 
	 * @param obj The annotated object's class.
	 * @param ann The annotation we need to read.
	 * @param property The annotation's property we need to read.
	 *  
	 * @return The annotation's value.
	 */	
	String getAnnotationValue(Class<? extends Annotation> ann, String property);
		
	/**
	 * Check if the class has annotations.
	 * 
	 * @param clazz Class to be checked.
	 * @return True if the class is annotated, False otherwise.
	 */
	boolean hasAnnotations();

	/**
	 * Check if the class has annotations.
	 * 
	 * @param clazz Class to be checked.
	 * @param ann Annotation to be checked.
	 * 
	 * @return True if the class is annotated with the given annotation, False otherwise.
	 */
	boolean hasAnnotations(Class<? extends Annotation> ann);
}
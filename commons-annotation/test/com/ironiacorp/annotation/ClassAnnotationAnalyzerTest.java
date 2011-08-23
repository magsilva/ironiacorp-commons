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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.datastructure.array.ArrayUtil;

public class ClassAnnotationAnalyzerTest
{
	private static Class<? extends Object> annotatedClass;
	private static Class<? extends Object> ordinaryClass;

	private static Class<? extends Annotation> validAnnotation;
	private static Class<? extends Annotation> invalidAnnotation;
	
	private final static String DEFAULT_VALUE = "test123";
	private final static String DEFAULT_FIELD_NAME = AnnotationAnalyzer.DEFAULT_PROPERTY;
	private final static String VALID_FIELD_NAME = "test";
	private final static String INVALID_FIELD_NAME = "asdfg";
	private final static Field[] validFields = new Field[1];
	private final static Field[] invalidFields = new Field[1];
	
	private ClassAnnotationAnalyzer classAnalyzer;
	
	private FieldAnnotationAnalyzer fieldAnalyzer;
	
	private DummyClass bean;
	
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DummyAnnotation
	{
		String value() default DEFAULT_VALUE;
		String test() default DEFAULT_VALUE;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DummyDummyAnnotation
	{
		String value() default DEFAULT_VALUE;
		String test() default DEFAULT_VALUE;
	}
	
	@DummyAnnotation
	public class DummyClass
	{
		@DummyAnnotation
		public String test;
		
		public String asdfg;
	}
	
	@Before
	public void setUp() throws Exception
	{
		bean = new DummyClass();
		validFields[0] = bean.getClass().getField("test"); 	
		invalidFields[0] = bean.getClass().getField("asdfg");
		
		annotatedClass = bean.getClass();
		ordinaryClass = String.class;
		
		validAnnotation = DummyAnnotation.class;
		invalidAnnotation = DummyDummyAnnotation.class;
		
		classAnalyzer = new ClassAnnotationAnalyzer();
		fieldAnalyzer = new FieldAnnotationAnalyzer();
	}

	@Test
	public void testGetAnnotationDefaultValue1()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertEquals(DEFAULT_VALUE, classAnalyzer.getAnnotationValue(validAnnotation));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAnnotationDefaultValue2()
	{
		classAnalyzer.setClazz(annotatedClass);
		classAnalyzer.getAnnotationValue(invalidAnnotation);
		fail();
	}

	@Test
	public void testGetAnnotationValue1()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertEquals(DEFAULT_VALUE, classAnalyzer.getAnnotationValue(validAnnotation, DEFAULT_FIELD_NAME));
	}

	@Test
	public void testGetAnnotationValue2()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertEquals(DEFAULT_VALUE, classAnalyzer.getAnnotationValue(validAnnotation, VALID_FIELD_NAME));
	}

	@Test
	public void testGetAnnotationValue3()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertFalse(DEFAULT_VALUE.equals(classAnalyzer.getAnnotationValue(validAnnotation, INVALID_FIELD_NAME)));
	}

	@Test
	public void testHasAnnotatiosClass1()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertTrue(classAnalyzer.hasAnnotations());
	}

	@Test
	public void testHasAnnotatiosClass2()
	{
		classAnalyzer.setClazz(ordinaryClass);
		assertFalse(classAnalyzer.hasAnnotations());
	}

	@Test
	public void testHasAnnotatiosClass3()
	{
		classAnalyzer.setClazz(annotatedClass);
		assertTrue(classAnalyzer.hasAnnotations(DummyAnnotation.class));
	}

	
	@Test
	public void testGetAnnotatedPropertiesClass1()
	{
		classAnalyzer.setClazz(annotatedClass);
		Field[] fields = classAnalyzer.getAnnotatedFields();
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClass2()
	{
		classAnalyzer.setClazz(annotatedClass);
		Field[] fields = classAnalyzer.getAnnotatedFields();
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClass3()
	{
		classAnalyzer.setClazz(ordinaryClass);
		Field[] fields = classAnalyzer.getAnnotatedFields();
		assertTrue(fields.length == 0);
	}

	
	@Test
	public void testGetAnnotatedPropertiesClassAnnotation1()
	{
		classAnalyzer.setClazz(annotatedClass);
		Field[] fields = classAnalyzer.getAnnotatedFields(validAnnotation);
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation2()
	{
		classAnalyzer.setClazz(annotatedClass);
		Field[] fields = classAnalyzer.getAnnotatedFields(validAnnotation);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation3()
	{
		classAnalyzer.setClazz(annotatedClass);
		Field[] fields = classAnalyzer.getAnnotatedFields(invalidAnnotation);
		assertTrue(fields.length == 0);
	}
}

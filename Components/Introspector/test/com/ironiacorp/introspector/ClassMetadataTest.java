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

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.introspector.reflection.ClassMetadata;
import com.ironiacorp.introspector.reflection.FieldMetadata;
import com.ironiacorp.introspector.reflection.TypeParameterMetadata;

public class ClassMetadataTest
{
	private class GenericTestClass<T>
	{
		public List<T> list;

		public T testMethod() throws Exception
		{
			return null;
		}
	}

	private class TestClass
	{
		public List list;

		public String testMethod() throws Exception
		{
			return null;
		}
	}

	private class TestClass2
	{
		public List<String> list;
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testField() throws Exception
	{
		// get the basic information
		Field field = TestClass2.class.getDeclaredField("list");
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Class rclas = (Class) ptype.getRawType();
		assertEquals(List.class, rclas);
		
		// list the type variables of the base class
		TypeVariable[] tvars = rclas.getTypeParameters();
		for (int i = 0; i < tvars.length; i++) {
			TypeVariable tvar = tvars[i];
		}
		
		// list the actual type arguments
	    Type[] targs = ptype.getActualTypeArguments();
	    for (int j = 0; j < targs.length; j++) {
	        Class tclas = (Class)targs[j];
	        assertEquals(String.class, tclas);
	    }
	}

	@Ignore
	@Test
	public void testField2() throws Exception
	{
		// get the basic information
		Field field = TestClass2.class.getDeclaredField("list");
		FieldMetadata fm = new FieldMetadata(field);
				
		assertEquals(List.class, fm.getType());
		
		TypeParameterMetadata[] tpm = fm.getTypeParameters();
		assertEquals(String.class, tpm[0].getArguments()[0]);
	}

	
	@Test
	public void testMethodGenericExceptionType() throws Exception
	{
		GenericTestClass<String> t = new GenericTestClass<String>();
		Method m = t.getClass().getMethod("testMethod", null);
		assertArrayEquals(m.getExceptionTypes(), m.getGenericExceptionTypes());
	}

	@Test
	public void testMethodExceptionType() throws Exception
	{
		TestClass t = new TestClass();
		Method m = t.getClass().getMethod("testMethod", null);
		assertArrayEquals(m.getExceptionTypes(), m.getGenericExceptionTypes());
	}

	@Ignore
	@Test
	public void testGetType()
	{
		GenericTestClass<String> t = new GenericTestClass<String>();
		ClassMetadata metadata = new ClassMetadata(t.getClass());
		assertEquals(String.class, metadata.getGenericTypes()[0]);
	}

	@Test
	public void testStringType()
	{
		Class clazz = "Teste".getClass();
		ClassMetadata metadata = new ClassMetadata(clazz);
	}

	@Ignore
	@Test
	public void testSimpleGenericType()
	{
		GenericTestClass<String> test = new GenericTestClass<String>();
		Class clazz = test.getClass();
		ClassMetadata metadata = new ClassMetadata(clazz);
		assertEquals(String.class, metadata.getGenericTypes()[0]);
	}
}

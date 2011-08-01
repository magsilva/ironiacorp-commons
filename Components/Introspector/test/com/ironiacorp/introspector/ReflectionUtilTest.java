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

import java.util.HashSet;
import java.util.Set;


import org.junit.Test;

public class ReflectionUtilTest
{
	private class ClassInitializationException
	{
		public ClassInitializationException()
		{
			throw new RuntimeException();
		}
	}
	
	private final static String validClassname = "java.lang.String";
	private final static Class validClass = String.class;
	private final static String invalidClassname = "Rosbife";
	
	@Test
	public void testLoadValidClass()
	{
		Class c = ReflectionUtil.loadClass(validClassname);
		assertNotNull(c);
		assertEquals(c, validClass);
	}

	@Test
	public void testLoadInvalidClass()
	{
		Class c = ReflectionUtil.loadClass(invalidClassname);
		assertNull(c);
	}

	@Test
	public void testLoadClassWithExceptionAtInitialization()
	{
		Class c = ReflectionUtil.loadClass(ClassInitializationException.class.getName());
		assertEquals(c, ClassInitializationException.class);
	}
	
	@Test
	public void testIsInstanceOf_Array_Class_Ok()
	{
		Class[] classes = {Number.class};
		assertTrue(ReflectionUtil.isInstanceOf(classes, Integer.class));
	}
	
	@Test
	public void testIsInstanceOf_Array_Object_Ok()
	{
		Class[] classes = {Number.class};
		assertTrue(ReflectionUtil.isInstanceOf(classes, Integer.valueOf(1)));
	}

	
	@Test
	public void testIsInstanceOf_Array_Class_Err()
	{
		Class[] classes = {Number.class};
		assertFalse(ReflectionUtil.isInstanceOf(classes, String.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsInstanceOf_Array_Class_Err_Null()
	{
		ReflectionUtil.isInstanceOf((Class[] ) null, null);
	}
	
	@Test
	public void testIsInstanceOf_Array_Object_Err()
	{
		Class[] classes = {Number.class};
		assertFalse(ReflectionUtil.isInstanceOf(classes, "abc"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsInstanceOf_Array_Object_Err_Null()
	{
		Class[] classes = {Number.class};
		ReflectionUtil.isInstanceOf(classes, (Object) null);
	}
	
	
	@Test
	public void testIsInstanceOf_Set_Class_Ok()
	{
		Set<Class> classes = new HashSet<Class>();
		classes.add(Number.class);
		assertTrue(ReflectionUtil.isInstanceOf(classes, Integer.class));
	}
	
	@Test
	public void testIsInstanceOf_Set_Object_Ok()
	{
		Set<Class> classes = new HashSet<Class>();
		classes.add(Number.class);
		assertTrue(ReflectionUtil.isInstanceOf(classes, Integer.valueOf(1)));
	}

	
	@Test
	public void testIsInstanceOf_Set_Class_Err()
	{
		Set<Class> classes = new HashSet<Class>();
		classes.add(Number.class);
		assertFalse(ReflectionUtil.isInstanceOf(classes, String.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsInstanceOf_Set_Class_Err_Null()
	{
		ReflectionUtil.isInstanceOf((Set) null, null);
	}
	
	public void testIsInstanceOf_Set_Class_Err_ArrayWithNull()
	{
		Class[] classes = new Class[1];
		classes[0] = null;
		assertFalse(ReflectionUtil.isInstanceOf(classes, String.class));
	}
	
	@Test
	public void testIsInstanceOf_Set_Object_Err()
	{
		Set<Class> classes = new HashSet<Class>();
		classes.add(Number.class);
		assertFalse(ReflectionUtil.isInstanceOf(classes, "abc"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsInstanceOf_Set_Object_Err_Null()
	{
		Set<Class> classes = new HashSet<Class>();
		classes.add(Number.class);
		ReflectionUtil.isInstanceOf(classes, (Object) null);
	}
}

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

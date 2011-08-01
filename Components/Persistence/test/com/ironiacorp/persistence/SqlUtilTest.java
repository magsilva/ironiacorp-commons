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

package com.ironiacorp.persistence;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.introspector.ReflectionUtil;
import com.ironiacorp.persistence.SqlUtil;


public class SqlUtilTest
{
	private final String validDriver = "com.mysql.jdbc.Driver";
	private final String invalidDriver = "mortadela";
	
	private class DummyClassLoader extends ClassLoader
	{
		public Class<?> findClass(String name)
		{
			return findLoadedClass(name);
		}
	}
	
	@Ignore
	@Test
	public void testIsDriverLoaded1()
	{
		ReflectionUtil.loadClass(validDriver);
		DummyClassLoader cl = new DummyClassLoader();
		assertNotNull(cl.findClass(validDriver));
	}

	@Test
	public void testIsDriverLoaded2()
	{
		DummyClassLoader cl = new DummyClassLoader();
		assertNull(cl.findClass(invalidDriver));
	}

	
	@Test
	public void testUnloadDriver1()
	{
		ReflectionUtil.loadClass(validDriver);
		SqlUtil.unloadDriver(validDriver);
		assertFalse(SqlUtil.isDriverLoaded(validDriver));
	}

	@Test
	public void testUnloadDriver2()
	{
		SqlUtil.unloadDriver(invalidDriver);
	}
		
	@Ignore
	@Test
	public void testLoadDriver()
	{
		SqlUtil.loadDriver(validDriver);
		assertTrue(SqlUtil.isDriverLoaded(validDriver));
	}
}

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

package com.ironiacorp.commons;


import static org.junit.Assert.*;

import org.junit.Test;

import com.ironiacorp.commons.ReflectionUtil;
import com.ironiacorp.commons.SqlUtil;

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
		
	@Test
	public void testLoadDriver()
	{
		SqlUtil.loadDriver(validDriver);
		assertTrue(SqlUtil.isDriverLoaded(validDriver));
	}
}

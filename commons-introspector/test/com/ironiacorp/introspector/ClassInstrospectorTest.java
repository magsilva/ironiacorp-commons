package com.ironiacorp.introspector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ClassInstrospectorTest
{
	private ClassInstrospector ci;
	
	@Before
	public void setUp() throws Exception
	{
		ci = new ClassInstrospector();
	}
	
	/*
	int baseNameSize = dir.getName().length();
		String className = dir.getName().substring(baseNameSize);
		className = className.replaceAll(File.separator, ReflectionUtil.PACKAGE_DELIMITER);
		if (className.endsWith(ReflectionUtil.CLASS_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.CLASS_FILE_EXTENSION));
		}
		if (className.endsWith(ReflectionUtil.JAVA_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.JAVA_FILE_EXTENSION));
		}
		
	 */
	
	@Ignore
	@Test
	public void testToString()
	{
		
		assertEquals("com.ironiacorp.Test", ci.toString("/arg", "/arg/com/ironiacorp/Test.java"));
	}
}

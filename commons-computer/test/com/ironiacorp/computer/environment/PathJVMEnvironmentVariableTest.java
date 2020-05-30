package com.ironiacorp.computer.environment;

import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.computer.environment.PathJVMEnvironmentVariable;

import static org.junit.Assert.*;


public class PathJVMEnvironmentVariableTest
{
	@Test
	public void testValidJVMEnvVar()
	{
		PathJVMEnvironmentVariable var = new PathJVMEnvironmentVariable("java.class.path");
		String[] packages = var.getValue();
		assertNotNull(packages);
		assertTrue(packages.length > 0);
	}

	@Ignore
	@Test
	public void testValidJVMEnvVar_Path()
	{
		PathJVMEnvironmentVariable var = new PathJVMEnvironmentVariable("property.test1");
		String[] packages = var.getValue();
		assertNotNull(packages);
		assertTrue(packages.length > 0);
		assertEquals("/opt/test.jar", packages[0]);
		assertEquals("/usr/lib", packages[1]);
	}
	
	@Ignore
	@Test
	public void testValidJVMEnvVar_Empty()
	{
		PathJVMEnvironmentVariable var = new PathJVMEnvironmentVariable("property.test2");
		String[] packages = var.getValue();
		assertNotNull(packages);
		assertEquals("", packages[0]);
	}
	
	@Test
	public void testInvalidJVMEnvVar()
	{
		PathJVMEnvironmentVariable var = new PathJVMEnvironmentVariable("fsafsdjafsal");
		String[] packages = var.getValue();
		assertTrue(packages.length == 0);
	}

}

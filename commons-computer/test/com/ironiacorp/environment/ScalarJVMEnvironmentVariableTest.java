package com.ironiacorp.environment;

import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.computer.environment.ScalarJVMEnvironmentVariable;

import static org.junit.Assert.*;


public class ScalarJVMEnvironmentVariableTest
{
	@Test
	public void testValidJVMEnvVar()
	{
		ScalarJVMEnvironmentVariable var = new ScalarJVMEnvironmentVariable("java.class.path");
		String value = var.getValue();
		assertNotNull(value);
	}

	@Ignore
	@Test
	public void testValidJVMEnvVar_Path()
	{
		ScalarJVMEnvironmentVariable var = new ScalarJVMEnvironmentVariable("property.test1");
		String packages = var.getValue();
		assertNotNull(packages);
		assertEquals("/opt/test.jar:/usr/lib", packages);
	}
	
	@Ignore
	@Test
	public void testValidJVMEnvVar_Empty()
	{
		ScalarJVMEnvironmentVariable var = new ScalarJVMEnvironmentVariable("property.test2");
		String packages = var.getValue();
		assertNotNull(packages);
		assertEquals("", packages);
	}
	
	@Test
	public void testInvalidJVMEnvVar()
	{
		ScalarJVMEnvironmentVariable var = new ScalarJVMEnvironmentVariable("fsafsdjafsal");
		String packages = var.getValue();
		assertNull(packages);
	}

}

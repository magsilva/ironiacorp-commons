package com.ironiacorp.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ironiacorp.computer.environment.PathSystemEnvironmentVariable;

public class ScalarSystemEnvironmentVariableTest
{
	/**
	 * PATH=/usr/local/bin:/usr/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin
	 */
	@Test
	public void testValidEnvVar_Path()
	{
		PathSystemEnvironmentVariable var = new PathSystemEnvironmentVariable("PATH");
		String[] paths = var.getValue();
		assertEquals("/usr/local/bin", paths[0]);
		assertEquals("/usr/bin", paths[1]);
		assertEquals("/bin", paths[2]);
		assertEquals("/usr/local/sbin", paths[3]);
		assertEquals("/usr/sbin", paths[4]);
		assertEquals("/sbin", paths[5]);
	}
	
	@Test
	public void testValidEnvVar_Empty()
	{
		PathSystemEnvironmentVariable var = new PathSystemEnvironmentVariable("EMPTY");
		String[] paths = var.getValue();
		assertNotNull(paths);
		assertEquals("", paths[0]);
	}
	
	@Test
	public void testInvalidEnvVar()
	{
		PathSystemEnvironmentVariable var = new PathSystemEnvironmentVariable("PATHAFLKJAJSL");
		assertNull(var.getValue());
	}
}

package com.ironiacorp.computer.environment;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.computer.environment.PathSystemEnvironmentVariable;

public class ScalarSystemEnvironmentVariableTest
{
	/**
	 * PATH=/usr/local/bin:/usr/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin
	 */
	@Ignore
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
	
	@Ignore
	@Test
	public void testValidEnvVar_DoesNotExists()
	{
		PathSystemEnvironmentVariable var = new PathSystemEnvironmentVariable("EMPTY");
		assertEquals("", var.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidEnvVar()
	{
		PathSystemEnvironmentVariable var = new PathSystemEnvironmentVariable("PATHAFLKJAJSL");
		assertNull(var.getValue());
	}
}

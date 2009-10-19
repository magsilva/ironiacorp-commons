package com.ironiacorp.license;


import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class VersionUtilTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testCompare0()
	{
		assertTrue(VersionUtil.compare("1", "1") == 0);
	}
	
	@Test
	public void testCompare1()
	{
		assertTrue(VersionUtil.compare("1.0", "1.0") == 0);
	}

	@Test
	public void testCompare2()
	{
		assertTrue(VersionUtil.compare("1.0", "1.1") == -1);
	}

	@Test
	public void testCompare3()
	{
		assertTrue(VersionUtil.compare("1.0", "0.9") == 1);
	}

	@Test
	public void testCompare4()
	{
		assertTrue(VersionUtil.compare("0.9", "1.0") == -1);
	}

	@Test
	public void testCompare5()
	{
		assertTrue(VersionUtil.compare("1.0", "1.0.0") == -1);
	}

	@Test
	public void testCompare6()
	{
		assertTrue(VersionUtil.compare("1.0.0", "1.0") == 1);
	}

	@Test
	public void testCompare7()
	{
		assertTrue(VersionUtil.compare("1.0", "0.9") == - VersionUtil.compare("0.9", "1.0"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testeCompare8()
	{
		VersionUtil.compare("1.0", "a");
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCompare9()
	{
		VersionUtil.compare("a", "1.0");
		fail();
	}
	
	/*
	 * The implementor must also ensure that the
	 * relation is transitive: (x.compareTo(y)>0 && y.compareTo(z)>0) implies x.compareTo(z)>0.
*/
	@Test
	public void testTransitive()
	{
		String x = "1.0";
		String y = "0.9";
		String z = "0.8";
		assertTrue(VersionUtil.compare(x, y) > 0 && VersionUtil.compare(y, z) > 0 && VersionUtil.compare(x, z) > 0);
	}
	
	@Test
	public void testCompare10()
	{
		String x = "1.0";
		String y = "1.0";
		String z = "0.8";
		
		assertTrue(VersionUtil.compare(x, y) == 0 && VersionUtil.compare(x, z) == VersionUtil.compare(y, z));
	}

	@Test
	public void testCompare11()
	{
		String x = "1.0";
		String y = "1.0";
		assertTrue((VersionUtil.compare(x, y) == 0) == x.equals(y));
	}
	
}

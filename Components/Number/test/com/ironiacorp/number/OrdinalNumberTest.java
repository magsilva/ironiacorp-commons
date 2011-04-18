package com.ironiacorp.number;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrdinalNumberTest
{
	private OrdinalNumber ordinal;

	@Before
	public void setUp() throws Exception
	{
		ordinal = new OrdinalNumber();
	}

	@Test
	public void testConvert_1st()
	{
		assertEquals(1, ordinal.convert("1st"));
	}

	@Test
	public void testConvert_Fist()
	{
		assertEquals(1, ordinal.convert("first"));
	}

	
	@Test
	public void testConvert_2nd()
	{
		assertEquals(2, ordinal.convert("2nd"));
	}

	@Test
	public void testConvert_Second()
	{
		assertEquals(2, ordinal.convert("second"));
	}

	
	@Test
	public void testConvert_3rd()
	{
		assertEquals(3, ordinal.convert("3rd"));
	}
	
	@Test
	public void testConvert_Third()
	{
		assertEquals(3, ordinal.convert("third"));
	}

	
	@Test
	public void testConvert_4th()
	{
		assertEquals(4, ordinal.convert("4th"));
	}

	@Test
	public void testConvert_Fourth()
	{
		assertEquals(4, ordinal.convert("fourth"));
	}

	
	@Test
	public void testConvert_12th()
	{
		assertEquals(12, ordinal.convert("12nd"));
	}
	
	@Test
	public void testConvert_16th()
	{
		assertEquals(16, ordinal.convert("16th"));
	}
	
	@Test
	public void testConvert_34th()
	{
		assertEquals(34, ordinal.convert("34th"));
	}
	
	@Test
	public void testConvert_Thirty_fourth()
	{
		assertEquals(34, ordinal.convert("thirty-fourth"));
	}
}

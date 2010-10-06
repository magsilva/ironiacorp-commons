package com.ironiacorp.number;

import static org.junit.Assert.*;

import org.junit.Test;

public class RomanNumeralTest
{
	@Test
	public void testRomanNumeralInt_Valid()
	{
		RomanNumeral number = new RomanNumeral(30);
		assertEquals(30, number.toInt());
		assertEquals("XXX", number.toString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralInt_Invalid_Low()
	{
		new RomanNumeral(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralInt_Invalid_High()
	{
		new RomanNumeral(4000);
	}


	@Test
	public void testRomanNumeralString_Valid()
	{
		RomanNumeral number = new RomanNumeral("XXX");
		assertEquals(30, number.toInt());
		assertEquals("XXX", number.toString());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_Empty()
	{
		new RomanNumeral("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_JustSpaces()
	{
		new RomanNumeral(" ");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_WithSpaces1()
	{
		new RomanNumeral("x Ix");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_WithSpaces2()
	{
		new RomanNumeral("x Ix v");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_NonRomanNumbers()
	{
		new RomanNumeral("xYz");
	}


	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_Null()
	{
		new RomanNumeral(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRomanNumeralString_Invalid_High()
	{
		new RomanNumeral("MMMCMXCX");
	}

	@Test
	public void testToString()
	{
		RomanNumeral number = new RomanNumeral("IV");
		assertEquals(4, number.toInt());
	}

	@Test
	public void testToString2()
	{
		RomanNumeral number = new RomanNumeral("L");
		assertEquals(50, number.toInt());
	}
	
	@Test
	public void testToString3()
	{
		RomanNumeral number = new RomanNumeral("LD");
		assertEquals(450, number.toInt());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testToString_High()
	{
		RomanNumeral number = new RomanNumeral("MMMCMXCX");
		assertEquals(3999, number.toInt());
	}

	@Test
	public void testToString_High_MixedCase()
	{
		RomanNumeral number = new RomanNumeral("MMmCMxciX");
		assertEquals(3999, number.toInt());
		assertEquals("MMMCMXCIX", number.toString());
	}

	@Test
	public void testToString_Low()
	{
		RomanNumeral number = new RomanNumeral("I");
		assertEquals(1, number.toInt());
	}
	

}

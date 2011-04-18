package com.ironiacorp.number;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CardinalNumberTest
{
	private CardinalNumber cardinal;

	@Before
	public void setUp() throws Exception {
		cardinal = new CardinalNumber();
	}

	@Test
	public void testConvert_0() {
		assertEquals(0, cardinal.convert("0"));
	}

	@Test
	public void testConvert_1() {
		assertEquals(1, cardinal.convert("1"));
	}

	@Test
	public void testConvert_2() {
		assertEquals(2, cardinal.convert("2"));
	}

	@Test
	public void testConvert_3() {
		assertEquals(3, cardinal.convert("3"));
	}

	@Test
	public void testConvert_4() {
		assertEquals(4, cardinal.convert("4"));
	}

	@Test
	public void testConvert_5() {
		assertEquals(5, cardinal.convert("5"));
	}

	@Test
	public void testConvert_6() {
		assertEquals(6, cardinal.convert("6"));
	}

	@Test
	public void testConvert_7() {
		assertEquals(7, cardinal.convert("7"));
	}

	@Test
	public void testConvert_8() {
		assertEquals(8, cardinal.convert("8"));
	}

	@Test
	public void testConvert_9() {
		assertEquals(9, cardinal.convert("9"));
	}

	@Test
	public void testConvert_10() {
		assertEquals(10, cardinal.convert("10"));
	}

	@Test
	public void testConvert_21() {
		assertEquals(21, cardinal.convert("21"));
	}
	
	@Test
	public void testConvert_101() {
		assertEquals(101, cardinal.convert("101"));
	}
	
	@Test
	public void testConvert_3010() {
		assertEquals(3010, cardinal.convert("3010"));
	}
	
	@Test
	public void testConvert_3010_WithPoint() {
		assertEquals(3010, cardinal.convert("3.010"));
	}
	
	@Test
	public void testConvert_3010_WithComma() {
		assertEquals(3010, cardinal.convert("3,010"));
	}
	
	@Test
	public void testConvert_0_Text() {
		assertEquals(0, cardinal.convert("zero"));
	}
	
	@Test
	public void testConvert_10_Text() {
		assertEquals(10, cardinal.convert("ten"));
	}
	
	@Test
	public void testConvert_16_Text() {
		assertEquals(16, cardinal.convert("sixteen"));
	}
	
	@Test
	public void testConvert_21_Text_1() {
		assertEquals(21, cardinal.convert("twenty-one"));
	}
	
	@Test
	public void testConvert_21_Text_2() {
		assertEquals(21, cardinal.convert("twenty and one"));
	}
	
	@Test
	public void testConvertMixed() {
		assertEquals(20000, cardinal.convert("20 thousand"));
	}
	
	@Test
	public void testConvert_BigNumber() {
		assertEquals(15006000019L, cardinal.convert("15 billion, 6 million, and nineteen"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvert_NotNumber() {
		cardinal.convert("million bytes");
	}
}

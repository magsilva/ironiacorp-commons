package com.ironiacorp.number;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases created using data from the following sites:
 * https://programming.guide/worlds-most-copied-so-snippet.html
 * https://programming.guide/java/formatting-byte-size-to-human-readable-format.html
 */
class HumanReadableBinaryNumberConverterTest {
	
	private HumanReadableBinaryNumberConverter converter;
	
	@BeforeEach
	public void setUp() {
		converter = new HumanReadableBinaryNumberConverter();
	}

	@Test
	public void testToHumanReadableByteCountSI() {
		assertEquals("0 B", converter.toSI(0));
		assertEquals("27 B", converter.toSI(27));
		assertEquals("999 B", converter.toSI(999));
		assertEquals("1.0 kB", converter.toSI(1000));
		assertEquals("1.9 TB", converter.toSI(1855425871872L));
	}

	@Test
	public void testToHumanReadableByteCountSI_NumberRounding() {
		assertEquals("1.0 kB", converter.toSI(1023));
		assertEquals("1.0 kB", converter.toSI(1024));
		assertEquals("1.8 kB", converter.toSI(1768));
	}

	
	@Test
	public void testToHumanReadableByteCountSI_MaxValue() {
		assertEquals("9.2 EB", converter.toSI(Long.MAX_VALUE));
	}

	@Test
	public void testToHumanReadableByteCountSI_OrderOfMagnitudeBoundary() {
		assertEquals("1.0 MB", converter.toSI(999999));
		assertEquals("999.9 PB", converter.toSI(999949999999999999L));
	}


	@Test
	public void testToHumanReadableByteCountSI_NegativeNumber() {
		assertEquals("-100 B", converter.toSI(-100));
		assertEquals("-10.0 kB", converter.toSI(-10000));
	}

	
	@Test
	public void testToHumanReadableByteCountBin() {
		assertEquals("0 B", converter.toBinary(0));
		assertEquals("27 B", converter.toBinary(27));
		assertEquals("999 B", converter.toBinary(999));
		assertEquals("1000 B", converter.toBinary(1000));
		assertEquals("1023 B", converter.toBinary(1023));
		assertEquals("1.0 KiB", converter.toBinary(1024));
		assertEquals("1.7 KiB", converter.toBinary(1768));
		assertEquals("1.7 TiB", converter.toBinary(1855425871872L));
		assertEquals("8.0 EiB", converter.toBinary(Long.MAX_VALUE));
	}

	@Test
	public void testToBinary_MaxValue() {
		assertEquals("8.0 EiB", converter.toBinary(Long.MAX_VALUE));
	}
	
	@Test
	public void testToBinary_NegativeNumber() {
		assertEquals("-100 B", converter.toBinary(-100));
		assertEquals("-9.8 KiB", converter.toBinary(-10000));
	}

}

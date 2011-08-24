package com.ironiacorp.ecc;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.ecc.crc32.Polynom;

public class CRCTest
{
	@Ignore
	@Test
	public void testCRC()
	{
		CRC crc = new CRC();
		assertEquals(0x25, crc.calculateUsingBitByBitFast("123456789".getBytes()));
	}
	
	@Ignore
	@Test
	public void testCRC2()
	{
		CRC crc = new CRC();
		Polynom poly = new Polynom();
		
		crc.setPolynom(poly);
		assertEquals(0x25, crc.calculateUsingBitByBit("123456789".getBytes()));
	}
}

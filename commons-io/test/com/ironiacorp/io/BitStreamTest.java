package com.ironiacorp.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.junit.Test;

public class BitStreamTest {

	@Test
	public void test() throws Exception {

		byte[] data = { (byte) 0xc6, 0x52, 0x65 };
		// 11000110 01010010 01100101
		// 1 10 001 1001010 01001100 101
		// 1 2 1 74 76 5
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream ds = new DataInputStream(bais);
		BitInputStream bs = new BitInputStream(ds);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		BitOutputStream bso = new BitOutputStream(dos);

		assertEquals(1, bs.getBits(1));
		assertEquals(2, bs.getBits(2));
		assertEquals(1, bs.getBits(3));
		assertEquals(74, bs.getBits(7));
		assertEquals(76, bs.getBits(8));
		assertEquals(5, bs.getBits(3));

		bso.putBits(1, 1);
		bso.putBits(2, 2);
		bso.putBits(1, 3);
		bso.putBits(74, 7);
		bso.putBits(76, 8);
		bso.putBits(5, 3);
		bso.flushToByte();

		byte[] b = baos.toByteArray();
		assertArrayEquals(data, b);
	}

}

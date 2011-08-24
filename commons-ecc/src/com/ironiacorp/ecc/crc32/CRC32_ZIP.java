package com.ironiacorp.ecc.crc32;

import com.ironiacorp.number.NumberConverter;

public class CRC32_ZIP
{
	public byte[] calculate(byte[] data)
	{
		java.util.zip.CRC32 x = new java.util.zip.CRC32();
		x.update(data);
		
		return NumberConverter.toBigEndianByteArray((int) x.getValue());
     }
}

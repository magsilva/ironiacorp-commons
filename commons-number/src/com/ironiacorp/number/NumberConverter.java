package com.ironiacorp.number;

public class NumberConverter
{
	public static byte[] toBigEndianByteArray(int value)
	{
		byte[] result =  new byte[] {
			(byte) (value >>> 24),
			(byte) (value >>> 16),
			(byte) (value >>> 8),
			(byte) value
		};
		return result;
	}

	public static byte[] toLittleEndianByteArray(int value)
	{
		byte[] result =  new byte[] {
			(byte) value,
			(byte) (value >>> 8),
			(byte) (value >>> 16),
			(byte) (value >>> 24),
		};
		return result;
	}

	
	public static int fromBigEndiantoInt(byte[] array)
	{
		int result = (array[0] << 24) + ((array[1] & 0xFF) << 16) + ((array[2] & 0xFF) << 8) + (array[3] & 0xFF);
		return result;
	}

	public static int fromLittleEndiantoInt(byte[] array)
	{
		int result = (array[3] << 24) + ((array[2] & 0xFF) << 16) + ((array[1] & 0xFF) << 8) + (array[0] & 0xFF);
		return result;
	}

	
}

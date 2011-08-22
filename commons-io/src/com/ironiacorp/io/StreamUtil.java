package com.ironiacorp.io;

import java.nio.charset.Charset;

public final class StreamUtil
{
	private StreamUtil()
	{
	}
	
	/**
	 * parse a numerical value that can be spanned over multiple bytes.
	 * 
	 * @param input Data input stream.
	 * @param len Number of bytes to read.
	 * @return Integer value.
	 * @throws IOException 
	 */
	public static final int readInt(byte[] data, int offset, int length)
	{
		int value = 0;
		for (int i = offset; i < (offset + length); i++) {
			value <<= 8;
			value += data[i] & 0xff;
		}
		return value;
	}
	

	public static final String readString(byte[] data, int offset, int length)
	{
		byte[] result = new byte[length];
		for (int i = 0, j = offset; i < length; i++, j++) {
			result[i] = data[j];
		}
		return new String(result, Charset.forName("UTF-8"));
	}
	
	public static final long convertInt(byte[] data)
	{
		return ((data[3] & 0xFF) << 24) | ((data[2] & 0xFF) << 16) | ((data[1] & 0xFF) << 8) | (data[0] & 0xFF);
	}

	public static final long convertInt(byte[] data, int offset)
	{
		byte[] target = new byte[4];
		System.arraycopy(data, offset, target, 0, target.length);
		return convertInt(target);
	}

	public static final int convertShort(byte[] data)
	{
		return ((data[0] & 0xFF) << 8) | (data[1] & 0xFF);
	}

	public static final int convertShort(byte[] data, int offset)
	{
		byte[] target = new byte[2];
		System.arraycopy(data, offset, target, 0, target.length);
		return convertShort(target);
	}
}

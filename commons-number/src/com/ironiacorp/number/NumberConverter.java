/*
Copyright (C) 2010 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


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

	/**
	 * Extracted from http://snippets.dzone.com/posts/show/93
	 */
	public static byte[] toBigEndianByteArray(long value)
	{
		byte[] result = new byte[] {
			(byte) (value >>> 56),
			(byte) (value >>> 48),
			(byte) (value >>> 40),
			(byte) (value >>> 32),
			(byte) (value >>> 24),
			(byte) (value >>> 16),
			(byte) (value >>> 8),
			(byte) value
		};
		return result;
	}

	/**
	 * Extracted from http://snippets.dzone.com/posts/show/93
	 */
	public static byte[] toLittleEndianByteArray(long value)
	{
		byte[] result = new byte[] {
			(byte) value,
			(byte) (value >>> 8),
			(byte) (value >>> 16),
			(byte) (value >>> 24),
			(byte) (value >>> 32),
			(byte) (value >>> 40),
			(byte) (value >>> 48),
			(byte) (value >>> 56),
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

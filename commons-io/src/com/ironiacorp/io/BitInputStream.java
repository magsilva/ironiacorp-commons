package com.ironiacorp.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * Version 1.0
 * Released under the GNU GPL
 * @author steev
 *
 */
public class BitInputStream {

	InputStream thisStream;
	short currentByte;
	int bitReady;	// which bit do we retrieve next. 8 for MSB, 1 for LSB, 0 for load next
	
	public BitInputStream(InputStream is) {
		thisStream = is;
		bitReady = 0;
	}
	
	public boolean getBoolean() throws IOException {
		return getBits(1) != 0 ? true : false;
	}
	
	public byte getByte() throws IOException {
		return (byte)getBits(8);
	}
	
	public short getShort() throws IOException {
		return (short)getBits(16);
	}
	
	public int getInt() throws IOException {
		return (int)getBits(32);
	}
	
	public long getLong() throws IOException {
		return (long)getBits(64);
	}
	
	public void getArray(short[] b) throws IOException {
		getArray(b, b.length * 8);
	}
	
	public void getArray(short[] b, int numBits) throws IOException {
		int idx = 0;
		if (numBits > b.length*8) {
			numBits = b.length*8;
		}
		
		while(numBits > 0) {
			int count = numBits > 8 ? 8 : numBits;
			b[idx] = (short)getBits(count);
			numBits -= count;
			++idx;
		}
	}
	
	public void getArray(byte[] b) throws IOException {
		getArray(b, b.length * 8);
	}
	
	public void getArray(byte[] b, int numBits) throws IOException {
		int idx = 0;
		if (numBits > b.length*8) {
			numBits = b.length*8;
		}
		
		while(numBits > 0) {
			int count = numBits > 8 ? 8 : numBits;
			b[idx] = (byte)getBits(count);
			numBits -= count;
			++idx;
		}
	}
	
	public long getBits(int numBits)  throws IOException {
		long result = 0;	// work with long to get as many digits of accuracy as possible
		while(numBits > 0) {
			// No more data left, so refill buffer
			if (bitReady == 0) {
				currentByte = getNextByte();
				bitReady = 8;
			}
			// 87654321
			// 76543210
			int shift = bitReady-numBits;
			if (shift >= 0) {
				// There are enough bits left in the current byte to take them all
				result <<= numBits; 
				result |= (currentByte >> shift) & ((1<<numBits)-1);
				bitReady -= numBits;
				return result;
			} else {
				// Read as many bits as we have left in the current byte
				// We then continue the loop.
				result <<= bitReady; 
				result |= (currentByte & ((1<<bitReady)-1));
				numBits -= bitReady;
				bitReady = 0;
			}
		}

		return result;
	}
		
	short getNextByte() throws IOException {
		// This casting ensures we can get unsigned data out
		return (short)(0xff & (int)thisStream.read());
	}
	
	//
	// Utility functions
	//
	/**
	 * This loads in all the bytes requested into a newly created data
	 * stream, that can be interrogated later. 
	 */
	public BitInputStream getStreamBlock(int numBytes) throws IOException {
		byte[] data = new byte[numBytes];
		
		getArray(data);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		return new BitInputStream(dis);
	}
}


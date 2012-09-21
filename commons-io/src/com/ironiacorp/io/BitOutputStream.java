package com.ironiacorp.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Version 1.0
 * Released under the GNU GPL
 * @author steev
 *
 */
public class BitOutputStream {

	OutputStream thisOutStream;
	short currentByte;
	int bitReady;	// which bit do we write into next. 8 for MSB, 1 for LSB, 0 for load next
	
	public BitOutputStream(OutputStream os) {
		thisOutStream = os;
		bitReady = 8;
		currentByte = 0;
	}

	public void putBits(long value, int numBits) throws IOException {
		// We need the 'numBits' LSB from value
		while(numBits > 0) {
			if (numBits > bitReady) {
				// Add the first bitReady bits, then flush
				
				// Move the msg bits down into the LSB positions
				// so 10000 is added as 10, for example.
				currentByte |= value >> (numBits-bitReady);
				
				numBits -= bitReady;
				flush();
			} else {
				// enough space for the whole thing 
				long v = value & ((1<<numBits)-1);
				
				currentByte |= v << (bitReady-numBits);
				bitReady -= numBits;
				numBits = 0;
			}
		}
	}
	
	public boolean isFlushReady() {
		return bitReady == 8 ? true : false;
	}
	
	public void flushToByte() throws IOException {
		flush();
	}
	
	protected void flush() throws IOException {
		thisOutStream.write(currentByte);
		bitReady = 8;
		currentByte = 0;
	}
}

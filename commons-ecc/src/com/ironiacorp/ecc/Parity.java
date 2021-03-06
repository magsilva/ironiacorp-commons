package com.ironiacorp.ecc;

import com.ironiacorp.number.NumberConverter;

/**
 * The parity makes the total number of 1-bits in the code vector (message
 * with parity bit appended) even (or odd). If a single bit gets altered
 * in transmission, this will change the parity from even to odd (or the
 * reverse).
 * 
 * The parity bit is generated by simply summing the message bits modulo
 * 2—that is, by exclusive or’ing them together. It then appends the parity
 * bit (or its complement) to the message. 
 * 
 * The parity bit can be checked by summing all the message bits modulo 2 and
 * checking that the sum agrees with the parity bit. Equivalently, the receiver
 * can sum all the bits (message and parity) and check that the result is 0 (if
 * even parity is being used).
 * 
 * This parity technique is often said to detect 1-bit errors. Actually it
 * detects errors in any odd number of bits (including the parity bit), but it
 * is a small comfort to know you are detecting 3-bit errors if you are
 * missing 2-bit errors.
 */
public class Parity
{
	public byte[] calculate(byte[] data)
	{
		int parity = 0;
		
		for (int i = 0; i < data.length; i++) {
			int x = data[i];
			int numOneBits = 0;
			
			for (int j = 1; j < 256; i *= 2) {
				if ((x & i) != 0) {
					numOneBits++;
			    }
			}
			parity = (parity + numOneBits) % 2;
		}
		
		return NumberConverter.toBigEndianByteArray(parity);
     }
}

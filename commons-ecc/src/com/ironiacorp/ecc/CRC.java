package com.ironiacorp.ecc;

import com.ironiacorp.ecc.crc32.Polynom;

/**
 * A CRC is an error-detecting code. Its computation resembles a long division
 * operation in which the quotient is discarded and the remainder becomes the
 * result, with the important distinction that the arithmetic used is the
 * carry-less arithmetic of a finite field.
 * 
 * Although CRCs can be constructed using any finite field, all commonly used
 * CRCs employ the finite field GF(2) (Galois field with two elements). This is
 * the field of two elements, usually called 0 and 1, comfortably matching
 * computer architecture.
 * 
 * Typically, an n-bit CRC, applied to a data block of arbitrary length, will
 * detect any single error burst not longer than n bits (in other words, any
 * single alteration that spans no more than n bits of the data), and will
 * detect a fraction 1-2-n of all longer error bursts. Errors in both data
 * transmission channels and magnetic storage media tend to be distributed
 * non-randomly (i.e. are "bursty"), making CRCs' properties more useful than
 * alternative schemes such as multiple parity checks.
 * 
 * However, CRCs are not, by themselves, suitable for protecting against
 * intentional alteration of data (for example, in authentication applications
 * for data security), because their convenient mathematical properties make it
 * easy to compute the CRC adjustment required to match any given change to the
 * data. That is, it is easy to generate other messages that result in the same
 * CRC, especially messages similar to the original. By design, however, a
 * message that is too similar (differing only by a trivial noise pattern) will
 * have a dramatically different CRC and thus be detected.
 * 
 * Therefore, CRCs can be relied upon to verify correctness but not integrity.
 * 
 * In contrast, an effective way to protect messages against intentional
 * tampering is by the use of a message authentication code such as HMAC.
 * 
 * Sometimes an implementation prefixes a fixed bit pattern to the bitstream to
 * be checked. This is useful when clocking errors might insert 0-bits in front
 * of a message, an alteration that would otherwise leave the CRC unchanged.
 * 
 * Sometimes an implementation appends n 0-bits (n being the size of the CRC) to
 * the bitstream to be checked before the polynomial division occurs. This has
 * the convenience that the CRC of the original bitstream with the CRC appended
 * is exactly zero, so the CRC can be checked simply by performing the
 * polynomial division on the expanded bitstream and comparing the remainder
 * with zero.
 * 
 * Sometimes an implementation exclusive-ORs a fixed bit pattern into the
 * remainder of the polynomial division.
 * 
 * Bit order: Some schemes view the low-order bit of each byte as "first", which
 * then during polynomial division means "leftmost", which is contrary to our
 * customary understanding of "low-order". This convention makes sense when
 * serial-port transmissions are CRC-checked in hardware, because some
 * widespread serial-port transmission conventions transmit bytes
 * least-significant bit first.
 * 
 * Byte order: With multi-byte CRCs, there can be confusion over whether the yte
 * transmitted first (or stored in the lowest-addressed byte of memory) is the
 * least-significant byte or the most-significant byte. For example, some 16-bit
 * CRC schemes swap the bytes of the CRC.
 * 
 * Omission of the high-order bit of the divisor polynomial: Since the
 * high-order bit is always 1, and since an n-bit CRC must be defined by an (n +
 * 1)-bit divisor which overflows an n-bit register, some writers assume that it
 * is unnecessary to mention the divisor's high-order bit.
 * 
 * There are two ways for the receiver to assess the correctness of the
 * transmission. It can compute the checksum from the first m bits of the
 * received data, and verify that it agrees with the last r received bits.
 * Alternatively, and following usual practice, the receiver can divide all the
 * m + r received bits by the generator polynomial and check that the r-bit
 * remainder is 0.
 */
public class CRC
{
	private Polynom poly;

	/**
	 * Use the direct algorithm, i.e. don't append Width paading zero bytes to
	 * the message.
	 */
	private boolean useDirect;

	private int directInit;
	private int nonDirectInit;

	/**
	 * Reflect input bytes.
	 * 
	 * Reflect the bytes of the message before processing them. A word is
	 * reflected by inverting the position of its bits with respect to the
	 * middle axis of the word.
	 * 
	 * Some CRC algorithms can be implemented more efficiently in a bit reversed
	 * version, thus many of the standard algorithmic variants use reflected
	 * input bytes.
	 */
	private boolean reflectInput;

	/**
	 * XOR input bytes.
	 * 
	 * The initial value (usually all 0 or all 1) in the algorithms which
	 * operate on the non-augmented message. This value can be seen as a value
	 * which will be XOR-ed into the CRC register after Width iterations of the
	 * bit-by-bit algorithm. This means the simple bit-by-bit algorithm must
	 * calculate the initial value with some sort of reverse CRC algorithm on
	 * the XorIn value.
	 */
	private int inputXor;

	/**
	 * Reflect output bytes.
	 * 
	 * Reflect the final CRC result. This operation takes place before XOR-ing
	 * the final CRC value with the XorOut parameter.
	 */
	private boolean reflectOutput;

	private int msbMask;

	private int mask;

	/**
	 * XOR output bytes
	 * 
	 * A value (usually all bits 0 or all 1) which will be XOR-ed to the final
	 * CRC value.
	 */
	private int outputXor;

	/**
	 * Return the direct init if the non-direct algorithm has been selected.
	 */
	private int getDirectInit(int init)
	{
		int crc = init;
		for (int i = 0; i < poly.getWidth(); i++) {
			int bit = crc & msbMask;
			crc <<= 1;
			if (bit != 0) {
				crc ^= poly.toInt();
			}
		}
		return crc & mask;
	}

	/**
	 * Return the non-direct init if the direct algorithm has been selected.
	 */
	private int getNonDirectInit(int init)
	{
		int crc = init;
		for (int i = 0; i < poly.getWidth(); i++) {
			int bit = crc & 0x01;
			if (bit != 0) {
				crc ^= poly.toInt();
			}
			crc >>= 1;
			if (bit != 0) {
				crc |= msbMask;
			}
		}
		return crc & mask;
	}

	/**
	 * Reflect a data word, i.e. reverts the bit order.
	 */
	private int reflect(int data, int size)
	{
		int x = data & 0x01;
		for (int i = 0; i < size; i++) {
			data >>= 1;
			x = (x << 1) | (data & 0x01);
		}
		return x;
	}

	/**
	 * Generates a scrambler lookup table for fast CRC computation. Use WIDTH
	 * bits to index the crc table; WIDTH one of {1, 2, 4, 8}.
	 */
	public int[] generateTable(Polynom poly, int tableIndexWidth)
	{
		int polynom = poly.toInt();
		if (poly.getWidth() % 8 != 0) {
			throw new IllegalArgumentException(
					"Cannot generate a table for polynomials which the width is not a multiple of 8");
		}
		if (tableIndexWidth != 1 || tableIndexWidth != 2 || tableIndexWidth != 4 || tableIndexWidth != 8) {
			throw new IllegalArgumentException("Invalid table width (should be one of {1, 2, 4, 8}");
		}

		final int[] crcTable = new int[1 << tableIndexWidth];

		// initialise scrambler table
		for (int i = 0; i < crcTable.length; i++) {
			int register = i;

			if (reflectInput) {
				register = reflect(register, tableIndexWidth);
			}

			register = register << (poly.getWidth() - tableIndexWidth);
			for (int j = 0; j < tableIndexWidth; j++) {
				if ((register & msbMask) != 0) {
					register = (register << 1) ^ polynom;
				} else {
					register = (register << 1);
				}
			}

			if (reflectInput) {
				register = reflect(register, poly.getWidth());
			}

			crcTable[i] = register & mask;
		}
		return crcTable;
	}

	/**
	 * Classic simple and slow CRC implementation.
	 * 
	 * This function iterates bit by bit over the augmented input message and
	 * returns the calculated CRC value at the end.
	 */
	public int calculateUsingBitByBit(byte[] data)
	{
		int register = nonDirectInit;
		for (int i = 0; i < data.length; i++) {
			int octet = data[i];
			if (reflectInput) {
				octet = reflect(octet, 8);
			}
			for (int j = 0; j < 8; j++) {
				int topbit = register & msbMask;
				register = ((register << 1) & mask) | ((octet >> 7) & 0x01);
				octet <<= 1;
				if (topbit != 0) {
					register ^= poly.toInt();
				}
			}
		}

		for (int i = 0; i < poly.getWidth(); i++) {
			int topbit = register & msbMask;
			register = (register << 1) & mask;
			if (topbit != 0) {
				register ^= poly.toInt();
			}
		}

		if (reflectOutput) {
			register = reflect(register, poly.getWidth());
		}
		return register ^ outputXor;
	}

	/**
	 * This is a slightly modified version of the bit-by-bit algorithm: it does
	 * not need to loop over the augmented bits, i.e. the Width 0-bits which are
	 * appended to the input message in the bit-by-bit algorithm.
	 */
	public int calculateUsingBitByBitFast(byte[] data)
	{
		int register = directInit;
		for (int i = 0; i < data.length; i++) {
			int octet = data[i];
			if (reflectInput) {
				octet = reflect(octet, 8);
				for (int j = 0; j < 8; j++) {
					int bit = register & msbMask;
					register <<= 1;
					if ((octet & (0x80 >> i)) != 0) {
						bit ^= msbMask;
					}
					if (bit != 0) {
						register ^= poly.toInt();
					}
				}
				register &= mask;
			}
		}
		if (reflectOutput) {
			register = reflect(register, poly.getWidth());
		}

		return register ^ outputXor;
	}

	public int calculateUsingTable(byte[] data)
	{
		int[] tbl = generateTable(poly, 8);

		int register = directInit;

		if (!reflectInput) {
			for (int i = 0; i < data.length; i++) {
				int tblidx = ((register >> (poly.getWidth() - 8)) ^ data[i]) & 0xff;
				register = ((register << 8) ^ tbl[tblidx]) & mask;
			}
		} else {
			register = reflect(register, poly.getWidth());
			for (int i = 0; i < data.length; i++) {
				int tblidx = (register ^ data[i]) & 0xff;
				register = ((register >> 8) ^ tbl[tblidx]) & mask;
			}
			register = reflect(register, poly.getWidth());
		}

		if (reflectOutput) {
			register = reflect(register, poly.getWidth());
		}

		return register ^ outputXor;
	}

	public void setPolynom(Polynom poly)
	{
		this.poly = poly;
		msbMask = 0x1 << (poly.getWidth() - 1);
		mask = ((msbMask - 1) << 1) | 1;

		if (useDirect) {
			directInit = inputXor;
			nonDirectInit = getNonDirectInit(inputXor);
		} else {
			directInit = getDirectInit(inputXor);
			nonDirectInit = inputXor;
		}
	}
}

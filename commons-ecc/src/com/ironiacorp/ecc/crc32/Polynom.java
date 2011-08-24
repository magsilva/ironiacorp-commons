package com.ironiacorp.ecc.crc32;

/**
 * The selection of generator polynomial is the most important part of
 * implementing the CRC algorithm. The polynomial must be chosen to maximise
 * the error detecting capabilities while minimising overall collision
 * probabilities.
 * 
 * The most important attribute of the polynomial is its length (the number
 * of the highest nonzero coefficient), because of its direct influence of
 * the length of the computed checksum.
 * 
 * When creating a new CRC polynomial or improving an existing CRC the
 * general mathematical advice is to use an irreducible polynomial that
 * satisfies all polynomical irreducibility constraints from modular
 * arithmetics.
 * <ul>
 * 	<li>Irreducibility in this case means that the polynomial cannot be
 * 	divided by any polynomial except itself and 1 with zero remainder.</li>
 * 	<li>Reducible polynomials can still be used, but their error correcting
 * 	and detecting capabilities will be less effective. Some applications may
 * 	choose to use reducible polynomials under certain conditions.</li>
 * </ul>
 * 
 * The properties of the generator polynomial can be derived from the
 * algorithm definition:
 * <ul>
 * 	<li>CRCs with more than one nonzero coefficients are able to detect
 * 	all single bit errors in the input message.</li>
 * 	<li>CRCs can be used to detect all double bit errors in the input
 * 	message shorter than 2^k, where k is the length of the longest
 * 	irreducible part of the polynomial.</li>
 * 	<li>If the CRC polynomial is divisible by x + 1 then no polynomial
 * 	with odd number of nonzero coefficients can be divided by it. Hence,
 * 	it can be used to detect odd number of errors in the input message
 * 	(like single bit parity function).</li>
 * 	<li>CRC polynomials detect (single) burst errors shorter than the number
 * 	of the position of the highest polynomial coefficient.</li>
 * </ul>
 */
public class Polynom
{
	/**
	 * Polynom degree.
	 * 
	 * The width of the CRC Poly, in number of bits. This is also the width
	 * of the final CRC result. For the bit-by-bit and the bit-by-bit-fast
	 * algorithms, any width can be used, but for the table-driven algorithm
	 * which operate on 8 bits at a time, only multiples of 8 can be chosen
	 * as Width. 
	 */
	private int width;
	
	public int toInt()
	{
		return 0;
	}
	
	public int getWidth()
	{
		return width;
	}

}

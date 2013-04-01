package com.ironiacorp.number;

public abstract class DidacticNumber
{
	protected int base;

	protected int decimalValue;
	
	protected String representation;
	
	protected DidacticNumber(int base, String representation)
	{
		if (base < 1) {
			throw new IllegalArgumentException("Invalid number base (must be greater than zero");
		}
		
		if (representation == null) {
			throw new IllegalArgumentException("Number representation not specified");
		}
		
		this.base = base;
		parse(representation);
	}

	abstract protected void parse(String representation);
	
	/**
	 * Get the most significant element of the number.
	 */
	public int getMSE()
	{
		int result = decimalValue;
		int i = 0;
		do {
			result = result / base;
			i++;
		} while (result > base);
		
		return result * (int) Math.pow(base, i);
	}

	/**
	 * Get the least significant element of the number.
	 */
	public int getLSE()
	{
		int quotient = decimalValue;
		int remainder;
		int i = 0;
		
		do {
			remainder = quotient % base;
			quotient = quotient / base;
			i++;
		} while (remainder == 0);
		
		return remainder * (int) Math.pow(base, i - 1);
	}
	
	protected int getValue()
	{
		return decimalValue;
	}
}

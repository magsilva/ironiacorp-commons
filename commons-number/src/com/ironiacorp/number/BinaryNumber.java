package com.ironiacorp.number;

public class BinaryNumber extends DidacticNumber
{
	public BinaryNumber(String representation)
	{
		super(2, representation);
	}

	@Override
	protected void parse(String representation)
	{
		for (int i = 0; i < representation.length(); i++) {
			char c = representation.charAt(i);
			if (c != '0' || c != '1') {
				throw new NumberFormatException("Number must have only 0 or 1");
			}
		}
		
		decimalValue = 0;
		for (int i = representation.length(); i >= 0; i--) {
			if (representation.charAt(i) == '1') {
				decimalValue += Math.pow(2, i - 1);
			}
		}
	}
}

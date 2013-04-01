package com.ironiacorp.number;

public class DecimalNumber extends DidacticNumber
{
	public DecimalNumber(String representation)
	{
		super(10, representation);
	}

	@Override
	protected void parse(String representation)
	{
		decimalValue = Integer.parseInt(representation);
	}
}


package com.ironiacorp;

public class NumberValidator
{
	public boolean isNaturalNumber(String number)
	{
		if (number == null) {
			return false;
		}
		
		number = number.replace("-", "");
		number = number.trim();
		if (number.isEmpty()) {
			return false;
		}
		
		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			if (! Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}

	public String createValidNaturalNumber(String number)
	{
		StringBuilder sb = new StringBuilder();
		
		if (number == null) {
			return "0";
		}
		
		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
}

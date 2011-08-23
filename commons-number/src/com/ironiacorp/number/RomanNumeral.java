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

/**
 * Roman numberal class.
 * 
 * It supports an integer between 1 and 3999. It can be constructed either from an integer or from a
 * string that represents a Roman numeral in this range.
 * 
 * The code was grabbed from http://www.faqs.org/docs/javap/c9/ex-9-3-answer.html.
 */
public class RomanNumeral
{
	private int num; // The number represented by this Roman numeral.

	/*
	 * The following arrays are used by the toString() function to construct the standard Roman
	 * numeral representation of the number. For each i, the number numbers[i] is represented by the
	 * corresponding string, letters[i].
	 */

	private static int[] numbers = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

	private static String[] letters = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

	/**
	 * Creates the Roman number.
	 * 
	 * @param arabic Number to be represented as a Roman number.
	 * 
	 * @throws IllegalArgumentException if arabic number is not in the range 1 to 3999
	 * (inclusive.)
	 */
	public RomanNumeral(int arabic)
	{
		if (arabic < 1) {
			throw new IllegalArgumentException("Value of RomanNumeral must be positive.");
		}
		
		if (arabic > 3999) {
			throw new IllegalArgumentException("Value of RomanNumeral must be 3999 or less.");
		}
		
		this.num = arabic;
	}

	/**
	 * Creates the Roman number.
	 * 
	 * @param roman String (written as a Roman number). Both upper and lower case
	 * letters are allowed.
	 * 
	 * @throws IllegalArgumentException if the Roman number cannot be parsed or if
	 * its arabic representation is greater than 3999 (inclusive).
	 */
	public RomanNumeral(String roman)
	{
		if (roman == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		roman = roman.trim();
		if (roman.length() == 0) {
			throw new IllegalArgumentException(new NumberFormatException("An empty string does not define a Roman numeral."));
		}

		roman = roman.toUpperCase();

		int i = 0;
		while (i < roman.length()) {
			char letter = roman.charAt(i);
			int number = letterToNumber(letter);
			i++;
			if (i == roman.length()) {
				// There is no letter in the string following the one we have just processed.
				// So just add the number corresponding to the single letter to arabic.
				num += number;
			} else {
				// Look at the next letter in the string. If it has a larger Roman numeral
				// equivalent than number, then the two letters are counted together as
				// a Roman numeral with value (nextNumber - number).
				int nextNumber = letterToNumber(roman.charAt(i));
				if (nextNumber > number) {
					// Combine the two letters to get one value, and move on to next position in string.
					num += (nextNumber - number);
					i++;
				} else {
					// Don't combine the letters. Just add the value of the one letter onto the number.
					num += number;
				}
			}
		}

		if (num > 3999) {
			throw new IllegalArgumentException("Roman numeral must have value 3999 or less.");
		}
	}

	/**
	 * Find the integer value of letter considered as a Roman numeral.
	 * 
	 * @param letter Roman letter to be converted to arabic number.
	 * 
	 * @return Arabic number.
	 * @throws NumberFormatException if letter is not a Roman numeral.
	 */
	private int letterToNumber(char letter)
	{

		switch (letter) {
			case 'I':
				return 1;
			case 'V':
				return 5;
			case 'X':
				return 10;
			case 'L':
				return 50;
			case 'C':
				return 100;
			case 'D':
				return 500;
			case 'M':
				return 1000;
			default:
				throw new NumberFormatException("Letter does not represent a number: " + letter);
		}
	}

	/**
	 * Return the standard representation of this Roman numeral.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		int N = num; // N represents the part of num that still has to be converted to Roman numeral representation.
		for (int i = 0; i < numbers.length; i++) {
			while (N >= numbers[i]) {
				sb.append(letters[i]);
				N -= numbers[i];
			}
		}
		return sb.toString();
	}

	/**
	 * Return the value of this Roman numeral as an int.
	 */
	public int toInt()
	{
		return num;
	}
}

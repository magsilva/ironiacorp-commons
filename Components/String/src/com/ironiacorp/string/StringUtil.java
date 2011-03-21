/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
 */

package com.ironiacorp.string;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for strings.
 */
public final class StringUtil
{
	/**
	 * We really don't want an instance of this class, so we create this private
	 * constructor.
	 */
	private StringUtil()
	{
	}

	/**
	 * Code for the transformation option that adds a suffix to a string.
	 */
	public static final int PREFIX = 1;

	/**
	 * Code for the transformation option that adds a prefix to a string.
	 */
	public static final int SUFFIX = 2;

	public String convert(final String str, final String charsetFrom, final String charsetTo)
	{
		try {
			final byte[] stringBytes = str.getBytes(charsetFrom);
			return new String(stringBytes, charsetTo);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Invalid charset", e);
		}
	}

	/**
	 * Apply the transformations defined at 'options' into a string. Unknown
	 * options will be ignored.
	 * 
	 * @param str
	 *            The string to be transformed.
	 * @param options
	 *            The transformations to be applied.
	 * 
	 * @return The string with the transformations applied.
	 */
	public static String transform(String str, Map<Integer, String> options)
	{
		if (options == null) {
			return str;
		}

		if (options.get(PREFIX) != null) {
			str = options.get(PREFIX) + str;
		}
		if (options.get(SUFFIX) != null) {
			str = str + options.get(SUFFIX);
		}

		return str;
	}

	/**
	 * Capitalize the first letter of a string.
	 * 
	 * @param str String to have its first letter capitalized.
	 * 
	 * @return String with the first word capitalized.
	 */
	public static String capitaliseFirstLetter(String str)
	{
		if (str == null) {
			return null;
		}
		
		switch (str.length()) {
			case 0:
				return str;
			case 1:
				return str.substring(0, 1).toUpperCase();
			default:
				return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}

	
	/**
	 * Check if the strings are similar.
	 * 
	 * @param str1
	 *            String to be compared.
	 * @param str2
	 *            String to be compared.
	 * 
	 * @return True if similar, False otherwise.
	 */
	public static boolean isSimilar(String str1, String str2)
	{
		return isSimilar(str1, null, str2, null);
	}

	/**
	 * Check if the strings are similar.
	 * 
	 * @param str1
	 *            String to be compared. Must not be null.
	 * @param options1
	 *            Transformation options that will be applied to str1
	 * @param str2
	 *            String to be compared. Must not be null.
	 * @param options2
	 *            Transformation options that will be applied to str2
	 * 
	 * @return True if similar, False otherwise. If any of the parameters 'str1'
	 *         and 'str2' are 'null', returns False too.
	 */
	public static boolean isSimilar(String str1, Map<Integer, String> options1, String str2,
			Map<Integer, String> options2)
	{
		if (str1 == null || str2 == null) {
			return false;
		}

		str1 = transform(str1, options1);
		str2 = transform(str2, options2);

		if (str1.equals(str2)) {
			return true;
		}

		if (str1.equalsIgnoreCase(str2)) {
			return true;
		}

		return false;
	}

	/**
	 * Find a similar word within a set of words.
	 * 
	 * @param set
	 *            Set of words to search within.
	 * @param str
	 *            Target word.
	 * 
	 * @return The similar word if found, null otherwise.
	 */
	public static String findSimilar(Set<String> set, String str)
	{
		return findSimilar(set, str, null);
	}

	/**
	 * Find a similar word within a set of words.
	 * 
	 * @param set
	 *            Set of words to search within.
	 * @param str
	 *            Target word.
	 * @param options
	 *            Options to be applied to the word before matching.
	 * 
	 * @return The similar word if found, null otherwise.
	 */
	public static String findSimilar(Set<String> set, String str, Map<Integer, String> options)
	{
		for (String s : set) {
			if (isSimilar(s, options, str, null)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Check if a string is empty. A String is empty if it's null, has a length
	 * of zero or is filled with white spaces.
	 * 
	 * @param str
	 *            The string to be checked.
	 * @return True if empty, False otherwise
	 */
	public static boolean isEmpty(String str)
	{
		return isEmpty(str, true);
	}

	/**
	 * Check if a string is empty. A String is empty if it's null, has a length
	 * of zero or is filled with white spaces.
	 * 
	 * @param str
	 *            The string to be checked.
	 * @return True if empty, False otherwise
	 */
	public static boolean isEmpty(String str, boolean discardBlanks)
	{
		if (str == null) {
			return true;
		}

		if (discardBlanks) {
			return (str.trim().length() == 0);
		} else {
			return (str.length() == 0);
		}
	}

	/**
	 * Check whether a string is an integer.
	 * 
	 * @param s String to be checked.
	 * @return True if the string represents an integer, false otherwise.
	 */
	public static boolean isInt(String s)
	{
		try {
			new Integer(s.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Check whether a string is a float.
	 * 
	 * @param s String to be checked.
	 * @return True if the string represents a float, false otherwise.
	 */
	public static boolean isFloat(String s)
	{
		try {
			new Float(s.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Check whether a string is a double.
	 * 
	 * @param s String to be checked.
	 * @return True if the string represents a double, false otherwise.
	 */
	public static boolean isDouble(String s)
	{
		try {
			new Double(s.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Calculate the SHA-1 digest code for the text.
	 * 
	 * @param text Text to be digested.
	 * @return Digest text as by the SHA-1 code.
	 */
	public static String digestSHA1(String text)
	{
		return digest(text, "SHA1");
	}

	/**
	 * Calculate the MD5 digest code for the text.
	 * 
	 * @param text Text to be digested.
	 * @return Digest text as by the MD5 code.
	 */
	public static String digestMD5(String text)
	{
		return digest(text, "MD5");
	}
	
	/**
	 * Create the hash/digest code for a given text.
	 * 
	 * @param text Text to be digested.
	 * @param algorithm Digest code to be used (usually MD5 or SHA1).
	 * 
	 * @return Digest code.
	 * @throws IllegalArgumentException if an invalid text (null) is set.
	 */
	public static String digest(String text, String algorithm)
	{
		if (text == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		byte[] hash = md.digest(text.getBytes());
		StringBuilder sb = new StringBuilder();
		
		for (byte b : hash) {
			String hex = Integer.toHexString(b);
			int len = hex.length();
			if (len == 1) {
				sb.append("0" + hex);
			} else {
				sb.append(hex.substring(len - 2, len));
			}
		}
		
		return sb.toString();
	}
	
	public static int countUpCaseLetters(String text)
	{
		if (text == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isUpperCase(c)) {
				count++;
			}
		}
		
		return count;
	}
}

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

package com.ironiacorp.commons;

import java.util.HashMap;
import java.util.Map;

public final class EncodingUtil
{
	private EncodingUtil()
	{
	}

	/**
	 * Mapping of chars to UTF strings.
	 */
	private static Map<String, String> translateChars;
	static {
		translateChars = new HashMap<String, String>();
		translateChars.put("´e", "é");
		translateChars.put("´a", "á");
		translateChars.put("´ı", "í");
		translateChars.put("´o", "ó");
	}

	public static String translate(String str)
	{
		for (String key : translateChars.keySet()) {
			str = str.replace(key, translateChars.get(key));
		}
		return str;
	}

	public static String encodeToDec(String str)
	{
		StringBuffer buf = new StringBuffer();
		int len = (str == null ? -1 : str.length());

		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
				buf.append(c);
			} else {
				buf.append("&#" + (int) c + ";");
			}
		}
		return buf.toString();
	}

	public static String encodeToHex(String str)
	{
		StringBuffer buf = new StringBuffer();
		int len = (str == null ? -1 : str.length());

		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
				buf.append(c);
			} else {
				buf.append("%" + Integer.toHexString(c));
			}
		}
		return buf.toString();
	}
	
	public static String encodeToIEEE(String str)
	{
		StringBuffer buf = new StringBuffer();
		int len = (str == null ? -1 : str.length());

		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
				buf.append(c);
			} else if (c == ' ') {
				buf.append("+");
			} else {
				buf.append("%" + Integer.toHexString(c));
			}
		}
		return buf.toString();
	}

	
}

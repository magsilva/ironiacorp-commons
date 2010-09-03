package com.ironiacorp.http;
/*
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

Copyright (C) 2008 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/



import java.net.HttpURLConnection;
import java.net.URL;

public final class UrlUtil
{
	public static boolean exists(String url)
	{
		try {
			return exists(new URL(url));
		} catch (Exception e) {
			return false;
		}
	}

	// http://www.rgagnon.com/javadetails/java-0059.html
	public static boolean exists(URL url)
	{
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * A better solution is to use an URI and convert it to URL (URI.toURL())
	 * and vice-versa.
	 */
	@Deprecated
	public static String encodeUrlParameter(String text)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			Character letter = text.charAt(i);
			switch (letter) {
				//  gen-delims
				case ':':
				case '/':
				case '?':
				case '#':
				case '[':
				case ']':
				case '@':
				//  sub-delims
				case '!':
				case '$':
				case '&':
				case '\'':
				case '(':
				case ')':
				case '*':
				case '+':
				case ',':
				case ';':
				case '=':
				// others
				case '"':
				case ' ':
					String code = Integer.toHexString(letter);
					sb.append('%');
					sb.append(code);
					break;
				default:
					// ALPHA / DIGIT / "-" / "." / "_" / "~"
					sb.append(letter);
			}

		}
		return sb.toString();
	}
}

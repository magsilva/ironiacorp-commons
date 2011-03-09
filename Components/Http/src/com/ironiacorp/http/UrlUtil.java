/*
 * Copyright (C) 2008 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.http;


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

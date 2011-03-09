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


import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.ironiacorp.http.UrlUtil;

// http://www.rgagnon.com/javadetails/java-0059.html
public class UrlUtilTest
{
	public static final String GOOD_URL = "http://www.rgagnon.com/howto.html"; 

	public static final String BAD_URL = "http://www.rgagnon.com/pagenotfound.html"; 

	public static final String INVALID_URL = "htp:/www.ironiacorp.com?/testabc";
	
	@Test
	public void testExistsString()
	{
		assertTrue(UrlUtil.exists(GOOD_URL));
	}

	@Test
	public void testExistsURL() throws MalformedURLException
	{
		assertTrue(UrlUtil.exists(new URL(GOOD_URL)));
	}

	@Test
	public void testDoNotExistsString()
	{
		assertFalse(UrlUtil.exists(BAD_URL));
	}

	@Test
	public void testDoNotExistsString_2()
	{
		assertFalse(UrlUtil.exists(INVALID_URL));
	}

	
	@Test
	public void testDoNotExistsURL() throws MalformedURLException
	{
		assertFalse(UrlUtil.exists(new URL(BAD_URL)));
	}

	@Test
	public void testEncodeUrlParameter_Space()
	{
		assertEquals("%20", UrlUtil.encodeUrlParameter(" "));
	}

	@Test
	public void testEncodeUrlParameter_DoubleQuote()
	{
		assertEquals("%22", UrlUtil.encodeUrlParameter("\""));
	}
	
	@Test
	public void testEncodeUrlParameter_SingleQuote()
	{
		assertEquals("%27", UrlUtil.encodeUrlParameter("'"));
	}
	
	@Test
	public void testEncodeUrlParameter_Word()
	{
		assertEquals("abc", UrlUtil.encodeUrlParameter("abc"));
	}

	@Test
	public void testEncodeUrlParameter_Words()
	{
		assertEquals("abc%20test", UrlUtil.encodeUrlParameter("abc test"));
	}
}

package com.ironiacorp.commons;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

// http://www.rgagnon.com/javadetails/java-0059.html
public class UrlUtilTest
{
	public static final String GOOD_URL = "http://www.rgagnon.com/howto.html"; 

	public static final String BAD_URL = "http://www.rgagnon.com/pagenotfound.html"; 

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
	public void testDoNotExistsURL() throws MalformedURLException
	{
		assertFalse(UrlUtil.exists(new URL(BAD_URL)));
	}

}

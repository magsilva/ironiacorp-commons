package com.ironiacorp.http;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class HttpJobTest
{
	private URI uri;

	private URI uriAll;

	private URL url;
	
	@Before
	public void setUp() throws Exception
	{
		uri = new URI("http://www.labes.icmc.usp.br");
		uriAll = new URI("*");
		url = new URL("http://www.labes.icmc.usp.br");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_InvalidURI()
	{
		HttpJob job = new HttpJob(null, (URI) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_ValidURI()
	{
		HttpJob job = new HttpJob(null, uri);
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_ValidJob_InvalidURI() 
	{
		HttpJob job = new HttpJob(HttpMethod.GET, (URI) null);
	}

	
	@Test
	public void testHttpJob_ValidJob_ValidURI()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
	}

	@Test
	public void testHttpJob_ValidJob_ValidURIAll()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uriAll);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_InvalidURL()
	{
		HttpJob job = new HttpJob(null, (URL) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_ValidURL()
	{
		HttpJob job = new HttpJob(null, url);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testHttpJob_ValidJob_InvalidURL()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, (URL) null);
	}
	
	@Test
	public void testHttpJob_ValidJob_ValidURL()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, url);
	}

	
	@Test
	public void testGetMethod()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		assertEquals(HttpMethod.GET, job.getMethod());
	}

	@Test
	public void testGetURI()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		assertEquals(uri, job.getUri());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetRequestHeader_Invalid()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		job.getRequestHeader(null);
	}

	@Test
	public void testGetRequestHeader_Valid_NotSet()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(result);
	}
	
	@Test
	public void testGetRequestHeader_Valid_SetNull()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		job.setRequestHeader(HttpRequestHeader.ACCEPT, null);
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(result);
	}

	@Test
	public void testGetRequestHeader_Valid_SetNotNull()
	{
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		job.setRequestHeader(HttpRequestHeader.ACCEPT, "true");
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertEquals("true", result);
	}
	

	@Test
	public void testGetRequestHeader_Valid_SetSet()
	{
		String before = null;
		String after = null;
		HttpJob job = new HttpJob(HttpMethod.GET, uri);
		
		before = job.setRequestHeader(HttpRequestHeader.ACCEPT, "true");
		after = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(before);
		assertEquals("true", after);
		
		before = job.setRequestHeader(HttpRequestHeader.ACCEPT, "false");
		after = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertEquals("true", before);
		assertEquals("false", after);

		before = job.setRequestHeader(HttpRequestHeader.ACCEPT, null);
		after = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertEquals("false", before);
		assertNull(after);
		
		before = job.setRequestHeader(HttpRequestHeader.ACCEPT, "true");
		after = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(before);
		assertEquals("true", after);
	}
	
	@Ignore
	@Test
	public void testGetResult()
	{
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetResult()
	{
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetResultFormat()
	{
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testSetResultFormat()
	{
		fail("Not yet implemented");
	}

}

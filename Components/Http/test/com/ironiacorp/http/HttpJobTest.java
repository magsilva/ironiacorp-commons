/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

import java.net.URI;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class HttpJobTest
{
	private HttpJob job;
	
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
		job = new HttpJob(null, (URI) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_ValidURI()
	{
		job = new HttpJob(null, uri);
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_ValidJob_InvalidURI() 
	{
		job = new HttpJob(HttpMethod.GET, (URI) null);
	}

	
	@Test
	public void testHttpJob_ValidJob_ValidURI()
	{
		job = new HttpJob(HttpMethod.GET, uri);
	}

	@Test
	public void testHttpJob_ValidJob_ValidURIAll()
	{
		job = new HttpJob(HttpMethod.GET, uriAll);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_InvalidURL()
	{
		job = new HttpJob(null, (URL) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHttpJob_InvalidJob_ValidURL()
	{
		job = new HttpJob(null, url);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testHttpJob_ValidJob_InvalidURL()
	{
		job = new HttpJob(HttpMethod.GET, (URL) null);
	}
	
	@Test
	public void testHttpJob_ValidJob_ValidURL()
	{
		job = new HttpJob(HttpMethod.GET, url);
	}

	
	@Test
	public void testGetMethod()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		assertEquals(HttpMethod.GET, job.getMethod());
	}

	@Test
	public void testGetURI()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		assertEquals(uri, job.getUri());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetRequestHeader_Invalid()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		job.getRequestHeader(null);
	}

	@Test
	public void testGetRequestHeader_Valid_NotSet()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(result);
	}
	
	@Test
	public void testGetRequestHeader_Valid_SetNull()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		job.setRequestHeader(HttpRequestHeader.ACCEPT, null);
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertNull(result);
	}

	@Test
	public void testGetRequestHeader_Valid_SetNotNull()
	{
		job = new HttpJob(HttpMethod.GET, uri);
		job.setRequestHeader(HttpRequestHeader.ACCEPT, "true");
		String result = job.getRequestHeader(HttpRequestHeader.ACCEPT);
		assertEquals("true", result);
	}
	

	@Test
	public void testGetRequestHeader_Valid_SetSet()
	{
		String before = null;
		String after = null;
		job = new HttpJob(HttpMethod.GET, uri);
		
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
}

/*
 * Copyright (C) 2009 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.io.IoUtil;

public class HttpMethodResultTest {
	
	private HttpMethodResult result;

	@Before
	public void setUp() throws Exception {
		result = new HttpMethodResult();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHttpMethodResult() {
		assertNull(result.getResponseHeader(HttpResponseHeader.ACCEPT_RANGES));
		assertEquals(0, result.getStatusCode());
	}

	@Test
	public void testGetStatusCode()
	{
		result.setStatusCode(300);
		assertEquals(300, result.getStatusCode());
	}

	@Test
	public void testSetContent()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		// assertNotNull(result.getContent());
		assertArrayEquals("test".getBytes(), IoUtil.toByteArray(content));
	}
	
	@Test
	public void testSetContent_PreserveContent()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setPreserveOriginalContent(true);
		result.setContent(content);
		assertNotNull(result.getContent());
		assertSame(content, result.getContent());
	}
	

	@Test
	public void testSetContent_Caching()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		assertNotNull(result.getContent());
		assertNotSame(content, result.getContent());
		result.cacheContent();
		assertNotSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
	}
	
	@Test
	public void testSetContent_Caching_PreserveContent()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		result.setPreserveOriginalContent(true);
		assertNotNull(result.getContent());
		assertSame(content, result.getContent());
		result.cacheContent();
		assertSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
	}
	
	
	@Test
	public void testSetContent_CachingTwice()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		assertNotNull(result.getContent());
		assertNotSame(content, result.getContent());
		result.cacheContent();
		assertNotSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
		result.cacheContent();
		assertNotSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
	}
	
	@Test
	public void testSetContent_CachingWithoutContent()
	{
		result.cacheContent();
	}
	
	@Test
	public void testGetContentAsArray()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		assertNotNull(result.getContent());
		assertNotSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
	}

	@Test
	public void testGetContentAsArray_PreserveContent()
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		result.setPreserveOriginalContent(true);
		assertNotNull(result.getContent());
		assertSame(content, result.getContent());
		assertArrayEquals("test".getBytes(), result.getContentAsByteArray());
	}
	
	@Test
	public void testGetContentAsFile() throws Exception
	{
		ByteArrayInputStream content = new ByteArrayInputStream("test".getBytes());
		result.setContent(content);
		File file = result.getContentAsFile();
		FileInputStream fis = new FileInputStream(file);
		assertArrayEquals("test".getBytes(), IoUtil.toByteArray(fis));
	}
	
	@Test
	public void testSetResponseHeader()
	{
		result.setResponseHeader(HttpResponseHeader.VIA, "localhost");
		assertEquals("localhost", result.getResponseHeader(HttpResponseHeader.VIA));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetResponseHeader_Null()
	{
		result.setResponseHeader(null, "localhost");
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetResponseHeader_Null()
	{
		result.getResponseHeader(null);
	}
	
	@Test
	public void testGetResponseHeader_NotSet()
	{
		assertNull(result.getResponseHeader(HttpResponseHeader.ACCEPT_RANGES));
	}
	
	@Test
	public void testSetHost()
	{
		result.setHost("localhost");
		assertEquals("localhost", result.getHost());
	}
}

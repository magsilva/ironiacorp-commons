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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.Filesystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.io.IoUtil;

/**
 * Result of the execution of an HTTP method.
 */
public class HttpMethodResult
{
	/**
	 * Name or IP address of the server that responded to the method.
	 */
	private String host;
	
	/**
	 * HTTP code returned by the HTTP server after executing the method.
	 */
	private int statusCode;

	/**
	 * Header of the HTTP response.
	 */
	private Map<HttpResponseHeader, String> responseHeader;
	
	/**
	 * Content returned by the server.
	 */
	private InputStream content;
	
	/**
	 * Copy of the content.
	 */
	private byte[] contentCache;
	
	/**
	 * Controls whether the original input stream should be kept around (and open)
	 * after read to cache.
	 */
	private boolean preserveOriginalContent = false;

	/**
	 * Initialize a result.
	 */
	public HttpMethodResult()
	{
		responseHeader = new HashMap<HttpResponseHeader, String>();
	}
	
	/**
	 * Return the status of the execution of the HTTP method.
	 * 
 	 * @return HTTP exit code.
	 */
	public int getStatusCode()
	{
		return statusCode;
	}

	/**
	 * Set the status for the HTTP response. This method is expected to
	 * be set only the library that implements the HttpJobRunner interface
	 * (ie., it should not be set by the client).
	 * 
	 * @param statusCode Status code of the HTTP method.
	 */
	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	/**
	 * Set the input stream (that corresponds to the answer of the
	 * method).
	 * 
	 * The current implementation just keep the reference: it expects
	 * the input stream to be kept open until garbaged collected or
	 * explicitly closed by the HTTP library). Alternatively, you can
	 * call {@link #cacheContent()} to save the content locally.
	 * 
	 * @param content
	 */
	public void setContent(InputStream content)
	{
		this.content = content;
	}	

	/**
	 * Cache the response's content.
	 */
	public void cacheContent()
	{
		// If there is nothing to cache, return.
		if (content == null) {
			return;
		}
		
		// If something was already loaded in the cache, just skip caching it again.
		if (contentCache != null) {
			return;
		}

		contentCache = IoUtil.toByteArray(content);
		if (! preserveOriginalContent) {
			content = new ByteArrayInputStream(contentCache);
		}
	}
	
	/**
	 * Return an input stream with the response content.
	 * 
	 * @return Input stream with the response content.
	 */
	public InputStream getContent()
	{
		if (preserveOriginalContent) {
			return content;
		} else {
			cacheContent();
			return new ByteArrayInputStream(contentCache);
		}
	}

	/**
	 * Get the content as a byte array.
	 * 
	 * @return Byte array of the response's content.
	 */
	public byte[] getContentAsByteArray()
	{
		if (content == null) {
			throw new UnsupportedOperationException();
		}
		
		cacheContent();

		return contentCache.clone();
	}
	
	/**
	 * Get the content as a file.
	 * 
	 * @return File (a temporary one) with the content.
	 */
	public File getContentAsFile()
	{
		if (content == null) {
			throw new UnsupportedOperationException();
		}

		cacheContent();

		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		Filesystem fs = os.getFilesystem();
		return IoUtil.toFile(new ByteArrayInputStream(contentCache), fs.createTempFile());
	}
	
	/**
	 * Configure the response header of the response. It should be set only
	 * by the HTTP library (and not by a client).
	 * 
	 * @param header Name of the header to be set.
	 * @param value Value of the header.
	 * 
	 * @return Previous value of the header.
	 */
	public String setResponseHeader(HttpResponseHeader header, String value)
	{
		if (header == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		return responseHeader.put(header, value);
	}
	
	/**
	 * Get the response header.
	 * 
	 * @param header Header to get the value of.
	 * 
	 * @return Value of the header.
	 */
	public String getResponseHeader(HttpResponseHeader header)
	{
		if (header == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		return responseHeader.get(header);
	}

	/**
	 * Get the name of IP address of the server that provided the response.
	 * 
	 * @return Name or IP address of the server.
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * Set the name of IP address of the server that provided the response.
	 * 
	 * @param host Server name.
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	
	public boolean isPreserveOriginalContent() {
		return preserveOriginalContent;
	}

	public void setPreserveOriginalContent(boolean preserveOriginalContent) {
		this.preserveOriginalContent = preserveOriginalContent;
	}

}

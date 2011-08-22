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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * An HTTP job is a descriptor for an HTTP operation, i.e., the HTTP
 * request type and its parameters.
 */
public class HttpJob
{
	/**
	 * HTTP method.
	 */
	private HttpMethod method;
	
	/**
	 * Target URI.
	 */
	private URI uri;
	
	/**
	 * HTTP method request method headers.
	 */
	private Map<HttpRequestHeader, String> requestHeader;
	
	/**
	 * Job result.
	 */
	private HttpMethodResult result;
	
	/**
	 * Optional parameters.
	 */
	private Map<String, Object> parameters;
	
	/**
	 * Initializer of the current object. This is must only be called by constructor
	 * methods.
	 */
	private void init(HttpMethod method, URI uri)
	{
		if (method == null) {
			throw new IllegalArgumentException("No HTTP method was choosen");
		}
		
		if (uri == null) {
			throw new IllegalArgumentException("No target URI was defined");
		}
		
		this.method = method;
		this.uri = uri;
		this.requestHeader = new HashMap<HttpRequestHeader, String>();
		this.parameters = new HashMap<String, Object>();
	}
	
	/**
	 * Create a HTTP job.
	 * 
	 * @param method HTTP method to be executed (GET, POST, etc).
	 * @param url Target URL of the HTTP method.

	 * @throws URISyntaxException 
	 */
	public HttpJob(HttpMethod method, URL url)
	{
		if (url == null) {
			throw new IllegalArgumentException("Invalid URL");
		}
		try {
			init(method, url.toURI());	
		} catch (URISyntaxException se) {
			throw new IllegalArgumentException(se);
		}
	}

	
	/**
	 * Create a HTTP job.
	 * 
	 * @param method HTTP method to be executed (GET, POST, etc).
	 * @param uri Target URI of the HTTP method.
	 */
	public HttpJob(HttpMethod method, URI uri)
	{
		init(method, uri);	
	}
	
	public HttpMethod getMethod()
	{
		return method;
	}

	public URI getUri()
	{
		return uri;
	}

	public HttpMethodResult getResult()
	{
		return result;
	}

	public void setResult(HttpMethodResult response)
	{
		this.result = response;
	}
	

	
	public String setRequestHeader(HttpRequestHeader header, String value)
	{
		if (header == null) {
			throw new IllegalArgumentException();
		}
		
		return requestHeader.put(header, value);
	}
	
	public String getRequestHeader(HttpRequestHeader header)
	{
		if (header == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		return requestHeader.get(header);
	}
	
	public Object addParameter(String name, Object value)
	{
		return parameters.put(name, value);
	}
	
	public Object getParameter(String name)
	{
		return parameters.get(name);
	}
	
	public Map<String, Object> getParameters()
	{
		return parameters;
	}
}

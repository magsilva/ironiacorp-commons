/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Copyright (C) 2010 Marco Aurélio Graciotto Silva <magsilva@icmc.usp.br>
*/

package com.ironiacorp.http;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.ironiacorp.io.IoUtil;

public class HttpMethodResult
{
	private int statusCode;
	
	private InputStream content;
	
	private Map<HttpResponseHeader, String> responseHeader;
	
	private String host;
	
	
	public HttpMethodResult()
	{
		responseHeader = new HashMap<HttpResponseHeader, String>();
	}
	
	
	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	public void setContent(InputStream content)
	{
		this.content = content;
	}	

	public InputStream getContent()
	{
		return content;
	}

	public byte[] getContentAsByteArray()
	{
		return IoUtil.toByteArray(content);
	}
	
	public File getContentAsFile()
	{
		return IoUtil.toFile(content);
	}
	
	public String setResposeHeader(HttpResponseHeader header, String value)
	{
		if (header == null) {
			throw new IllegalArgumentException();
		}
		
		return responseHeader.put(header, value);
	}
	
	public String getResponseHeader(HttpResponseHeader header)
	{
		if (header == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		return responseHeader.get(header);
	}


	public String getHost()
	{
		return host;
	}


	public void setHost(String host)
	{
		this.host = host;
	}
}

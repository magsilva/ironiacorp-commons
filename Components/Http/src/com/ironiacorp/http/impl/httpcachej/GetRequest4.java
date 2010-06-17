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

Copyright (C) 2007 Apache Software Foundation (ASF).
 */

package com.ironiacorp.http.impl.httpcachej;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.Callable;

import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.cache.HTTPCache;
import org.codehaus.httpcache4j.payload.Payload;

import com.ironiacorp.io.IoUtil;
import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;
import com.ironiacorp.http.HttpMethodResultFormat;

/**
 * How to send a request via proxy using {@link HttpClient HttpClient}.
 * 
 * @author Roland Weber
 */
public class GetRequest4<T> implements Callable<HttpJob<T>>
{
	private HttpJob<T> job;
	
	private HTTPCache cache;

	public GetRequest4(HTTPCache cache, HttpJob<T> job)
	{
		this.cache = cache;
		this.job = job;
	}

	@SuppressWarnings("unchecked")
	public HttpJob<T> call()
	{
		URI uri = (URI) job.getParameter(0);
		HTTPRequest request = new HTTPRequest(uri);
		try {
			HTTPResponse response = cache.doCachedRequest(request);
	        Payload payload = response.getPayload();
		        
	        if (payload != null && payload.isAvailable()) {
	        	InputStream inputStream = payload.getInputStream();
	        	if (inputStream != null) {
	        		HttpMethodResult<T> result = new HttpMethodResult<T>();
	    			OutputStream outputStream = null;
	    			int readBytes = 0;
	    			byte[] buffer = new byte[IoUtil.BUFFER_SIZE];
	
	    			try {
	    				if (job.getResultFormat() == HttpMethodResultFormat.FILE) {
	    	    			File file = IoUtil.createTempFile("sysrev-get-", ".html");
	        				outputStream = new FileOutputStream(file);
	        			} else if (job.getResultFormat() == HttpMethodResultFormat.MEM) {
	        				outputStream = new ByteArrayOutputStream();
	        			} else {
	        				throw new UnsupportedOperationException("Output content format not supported");
	        			}
						
	        			while ((readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
	        				outputStream.write(buffer, 0, readBytes);
	        			}
	        			
	        			result.setContent((T) outputStream);
	        			result.setStatusCode(response.getStatus().getCode());
	        			job.setResult(result);
	        		} finally {
	        			try {
	        				outputStream.close();
	        				inputStream.close();
	        			} catch (Exception e) {
	        			}
	        		}
				}
	        }
		} catch (Exception e) {
		}
        return job;
	}
}

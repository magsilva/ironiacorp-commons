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

package com.ironiacorp.http.impl.httpclient4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.BasicHttpContext;

import com.ironiacorp.io.IoUtil;
import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;

/**
 * How to send a request via proxy using {@link HttpClient HttpClient}.
 * 
 * @author Roland Weber
 */
public class GetRequest4 implements Callable<HttpJob>
{
	private HttpJob job;

	private final HttpClient httpClient;

	private final HttpContext context;

	public GetRequest4(HttpClient httpClient, HttpJob job)
	{
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.job = job;
	}

	public HttpJob call()
	{
		URI uri = (URI) job.getParameter(0);
		HttpGet getMethod = new HttpGet(uri);
		try {
	        HttpResponse response = httpClient.execute(getMethod, context);
	        HttpEntity entity = response.getEntity();
		        
	        if (entity != null) {
	        	InputStream inputStream = entity.getContent();
	        	if (inputStream != null) {
	        		HttpMethodResult result = new HttpMethodResult();
	    			OutputStream outputStream = null;
	    			int readBytes = 0;
	    			byte[] buffer = new byte[IoUtil.BUFFER_SIZE];
	
	    			try {
	        			if (job.isSaveContentToFile()) {
	        				File file = IoUtil.createTempFile("sysrev-get-", ".html");
	        				outputStream = new FileOutputStream(file);
	        			} else {
	        				outputStream = new ByteArrayOutputStream();
	        			}
						
	        			while ((readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
	        				outputStream.write(buffer, 0, readBytes);
	        			}
	        			
	        			result.setContent(outputStream);
	        			result.setStatusCode(response.getStatusLine().getStatusCode());
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
			getMethod.abort();
		}
        return job;
	}
}

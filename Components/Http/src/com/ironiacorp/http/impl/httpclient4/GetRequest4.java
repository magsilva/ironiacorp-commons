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

import java.io.InputStream;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

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
	
	private HttpGet getMethod;

	public GetRequest4(HttpClient httpClient, HttpContext context, HttpJob job)
	{
		this.httpClient = httpClient; 
		this.context = context;
		this.job = job;
		this.getMethod = new HttpGet(job.getUri());
	}

	public HttpJob call()
	{
		try {
	        HttpResponse response = httpClient.execute(getMethod, context);
	        HttpEntity entity = response.getEntity();
		        
	        if (entity != null) {
	        	InputStream inputStream = entity.getContent();
	        	if (inputStream != null) {
	        		HttpMethodResult result = new HttpMethodResult();
	        	    HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
	    			result.setContent(inputStream);
	        		result.setStatusCode(response.getStatusLine().getStatusCode());

	        	    result.setHost(currentHost.getHostName());
	        		job.setResult(result);

				}
	        }
		} catch (Exception e) {
			getMethod.abort();
		}
		
        return job;
	}
}

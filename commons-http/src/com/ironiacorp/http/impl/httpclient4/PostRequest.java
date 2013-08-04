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


package com.ironiacorp.http.impl.httpclient4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;


public class PostRequest implements Callable<HttpJob>
{
	private HttpJob job;

	private final HttpClient httpClient;

	private final HttpClientContext context;
	
	private HttpPost method;
	
	private List<NameValuePair> parameters;

	public PostRequest(HttpClient httpClient, HttpClientContext context, HttpJob job)
	{
		this.httpClient = httpClient; 
		this.context = context;
		this.job = job;
		this.method = new HttpPost(job.getUri());
		this.parameters = new ArrayList<NameValuePair>(5);
		setParameters();
	}

	private void setParameters()
	{
		for (String name : job.getParameters().keySet()) {
			Object value = job.getParameter(name);
			NameValuePair pair = new BasicNameValuePair(name, value.toString());
			if (parameters.contains(pair)) {
				parameters.remove(pair);
			}
			parameters.add(pair);
		}
	}
	
	public HttpJob call()
	{
        HttpResponse response = null;
        HttpEntity entity = null;

		try {
	        response = httpClient.execute(method, context);
	        entity = response.getEntity();
		        
	        if (entity != null) {
	        	InputStream inputStream = entity.getContent();
	        	if (inputStream != null) {
	        		HttpMethodResult result = new HttpMethodResult();
	        	    HttpHost currentHost = context.getTargetHost();
	    			result.setContent(inputStream);
	        		result.setStatusCode(response.getStatusLine().getStatusCode());

	        	    result.setHost(currentHost.getHostName());
	        		job.setResult(result);
				}
	        }
		} catch (Exception e) {
			method.abort();
		}
		
        return job;
	}
}

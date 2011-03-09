/*
 * Copyright (C) 2010 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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


package com.ironiacorp.http.impl.httpclient3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;


public class PostRequest implements Callable<HttpJob>
{
	private HttpJob job;
	
	private HttpClient client;
	
	private List<NameValuePair> parameters;
	
	public PostRequest(HttpClient client, HttpJob job)
	{
		this.client = client;
		this.job = job;
		this.parameters = new ArrayList<NameValuePair>(5);
		setParameters();
	}
	
	private void setParameters()
	{
		for (String name : job.getParameters().keySet()) {
			Object value = job.getParameter(name);
			NameValuePair pair = new NameValuePair(name, value.toString());
			if (parameters.contains(pair)) {
				parameters.remove(pair);
			}
			parameters.add(pair);
		}
	}
	
	public HttpJob call()
	{
		URI uri = (URI) job.getUri();
		PostMethod postMethod = new PostMethod(uri.toString());
		HttpMethodResult result = new HttpMethodResult();
		postMethod.setRequestBody(parameters.toArray(new NameValuePair[0]));

		
		try {
			int statusCode = client.executeMethod(postMethod);
			
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			
			if (inputStream != null) {
				result.setContent(inputStream);
        		result.setStatusCode(statusCode);
        		job.setResult(result);
        		result.cacheContent();
				postMethod.releaseConnection();
			}
		} catch (HttpException e) {
		} catch (IOException e) {
			// In case of an IOException the connection will be released
			// back to the connection manager automatically
		} catch (RuntimeException ex) {
			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection and release it back to the connection manager.
			postMethod.abort();
		}
		
		return job;
	}
}

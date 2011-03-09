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

package com.ironiacorp.http.impl.httpcachej;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.cache.HTTPCache;
import org.codehaus.httpcache4j.payload.FormDataPayload;
import org.codehaus.httpcache4j.payload.Payload;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;

/**
 * HTTP Get method implementation using HttpCacheJ.
 * 
 * You should not use this class directly: HttpJobRunnerCacheJ will use
 * manage its use automatically.
 */
class PostRequest implements Callable<HttpJob>
{
	private HttpJob job;
	
	private HTTPCache cache;
	
	private List<FormDataPayload.FormParameter> parameters;

	
	public PostRequest(HTTPCache cache, HttpJob job)
	{
		this.cache = cache;
		this.job = job;
		this.parameters = new ArrayList<FormDataPayload.FormParameter>(5);
		setParameters();
	}
	
	private void setParameters()
	{
		for (String name : job.getParameters().keySet()) {
			Object value = job.getParameter(name);
			FormDataPayload.FormParameter pair = new FormDataPayload.FormParameter(name, value.toString());
			if (parameters.contains(pair)) {
				parameters.remove(pair);
			}
			parameters.add(pair);
		}
	}
	
	/**
	 * Run the job (thus retrieving a resource using HTTP's GET method).
	 * The result will be saved to a HttpResponse, available in the
	 * HttpJob.
	 */
	public HttpJob call()
	{
		URI uri = job.getUri();
		HTTPRequest request = new HTTPRequest(uri);
		HTTPResponse response = null;
		FormDataPayload formPayload = new FormDataPayload(parameters);
		request = request.payload(formPayload);
		
		try {
			response = cache.doCachedRequest(request);
	        Payload payload = response.getPayload();
		        
	        if (payload != null && payload.isAvailable()) {
	        	InputStream inputStream = payload.getInputStream();
	        	if (inputStream != null) {
	        		HttpMethodResult result = new HttpMethodResult();
        			result.setContent(inputStream);
	        		result.setStatusCode(response.getStatus().getCode());
	        		job.setResult(result);
				}
	        }
		} catch (Exception e) {
		}
        return job;
	}
}

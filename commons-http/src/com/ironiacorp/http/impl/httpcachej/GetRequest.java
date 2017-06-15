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
import java.util.Optional;
import java.util.concurrent.Callable;

import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.cache.HTTPCache;
import org.codehaus.httpcache4j.payload.Payload;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;

/**
 * HTTP Get method implementation using HttpCacheJ.
 * 
 * You should not use this class directly: HttpJobRunnerCacheJ will use
 * manage its use automatically.
 */
class GetRequest implements Callable<HttpJob>
{
	private HttpJob job;
	
	private HTTPCache cache;

	
	public GetRequest(HTTPCache cache, HttpJob job)
	{
		this.cache = cache;
		this.job = job;
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
		try {
			response = cache.execute(request);
		        Optional<Payload> payload = response.getPayload();
		        
	        if (payload.isPresent() && payload.get().isAvailable()) {
	        	InputStream inputStream = payload.get().getInputStream();
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

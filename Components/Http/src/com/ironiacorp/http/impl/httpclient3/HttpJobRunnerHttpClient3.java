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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;



public class HttpJobRunnerHttpClient3 extends com.ironiacorp.http.impl.HttpClient implements HttpJobRunner
{
	private int maxThreadsCount = 3;
	
	private HttpClient httpClient;
	
	private void setupClient()
	{
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		// connectionManager.// maxConnectionsPerHost // maxTotalConnections
        httpClient = new HttpClient(connectionManager);

        // http://jakarta.apache.org/httpcomponents/httpclient-3.x/preference-api.html
        HttpClientParams params = httpClient.getParams();
		params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
		params.setParameter("http.socket.timeout", new Integer(60000));
		params.setParameter("http.protocol.content-charset", "UTF-8");
        params.setCookiePolicy(CookiePolicy.RFC_2109);
        
		
	}
	
	public HttpJobRunnerHttpClient3()
	{
		setupClient();
	}
	
	public void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool(maxThreadsCount);
		ExecutorCompletionService<HttpJob> queue = new ExecutorCompletionService<HttpJob>(executor);
		List<Future<?>> workers = new ArrayList<Future<?>>();
		
        for (HttpJob job : jobs) {
			if (HttpMethod.GET == job.getMethod()) {
				GetRequest request = new GetRequest(httpClient, job);
				Future<HttpJob> jobStatus = queue.submit(request);
				workers.add(jobStatus);
				continue;
			}
			if (HttpMethod.POST == job.getMethod()) {
				PostRequest request = new PostRequest(httpClient, job);
				Future<HttpJob> jobStatus = queue.submit(request);
				workers.add(jobStatus);
				continue;
			}
			// TODO: job cannot be handled, what to do?
		}

		while (! workers.isEmpty()) {
			Iterator<Future<?>> i = workers.iterator();
			while (i.hasNext()) {
				try {
					Future<?> future = i.next();
					// future.get(timeout, TimeUnit.MILLISECONDS);
					future.get();
					i.remove();
				// } catch (TimeoutException e) {
				} catch (InterruptedException ie) {
					System.out.println(ie.getMessage());
				} catch (ExecutionException ee) {
					System.out.println(ee.getMessage());
					i.remove();
				}
			}
		}
	
		executor.shutdown();
	}

	@Override
	public void abort() {
	}
}

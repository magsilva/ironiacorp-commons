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

Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
*/

package com.ironiacorp.http.impl.httpclient4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.ironiacorp.string.StringUtil;
import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;
import com.ironiacorp.http.methods.HttpGetMethod;

public class HttpJobRunner4 implements HttpJobRunner
{
	private int maxThreadsCount = 3;
	
	private int timeout = 100;

	private ClientConnectionManager cm;
	
	private HttpParams params;
	
	private List<HttpJob> jobs;
	
	private static final HttpMethod[] registeredHttpMethods =
	{
		new HttpGetMethod()
	};
	
	private void setupHttpParams()
	{
		params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(params, "UTF-8");
		// HttpProtocolParams.setUserAgent(params, "");
	}
	
	private void setupConnectionManager()
	{
		Scheme schemeHTTP = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		Scheme schemeHTTPS = new Scheme("https", 443, SSLSocketFactory.getSocketFactory());
		
		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(schemeHTTP);
		schemeRegistry.register(schemeHTTPS);

		// Create an HttpClient with the ThreadSafeClientConnManager.
		cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		// Increase max total connection to 200
		((ThreadSafeClientConnManager) cm).setMaxTotalConnections(200);
		// Increase default max connection per route to 20
		((ThreadSafeClientConnManager) cm).setDefaultMaxPerRoute(20);


	}
	
	public HttpJobRunner4()
	{
		setupHttpParams();
		setupConnectionManager();
		jobs = new ArrayList<HttpJob>();
	}
	
	public void addJob(HttpJob job)
	{
		boolean added = false;
		
		for (HttpMethod m : registeredHttpMethods) {
			if (m.checkHttpJob(job)) {
				jobs.add(job);
				added = true;
				break;
			}
		}
		
		if (! added) {
			throw new IllegalArgumentException("Invalid job");
		}
	}
	
	
	public void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool(maxThreadsCount);
		ExecutorCompletionService<HttpJob> queue = new ExecutorCompletionService<HttpJob>(executor);
		List<Future<?>> workers = new ArrayList<Future<?>>(); 
		HttpClient httpClient = new DefaultHttpClient(cm, params); 

		for (HttpJob job : jobs) {
			if (StringUtil.isSimilar(job.getMethod(), "GET")) {
				GetRequest4 request = new GetRequest4(httpClient, job);
				Future<HttpJob> jobStatus = queue.submit(request);
				workers.add(jobStatus);
				continue;
			}
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
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			}
		}
	
		executor.shutdown();
	}
	
	public void abort()
	{
		cm.shutdown();
	}


}
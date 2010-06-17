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

package com.ironiacorp.http.impl.httpcachej;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.codehaus.httpcache4j.cache.HTTPCache;
import org.codehaus.httpcache4j.cache.MemoryCacheStorage;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;

import com.ironiacorp.string.StringUtil;
import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;
import com.ironiacorp.http.methods.HttpGetMethod;

public class HttpJobRunnerCacheJ implements HttpJobRunner
{
	private int maxThreadsCount = 3;
	
	private int timeout = 100;

	private HTTPCache cache;
	
	private List<HttpJob> jobs;
	
	private static final HttpMethod[] registeredHttpMethods =
	{
		new HttpGetMethod()
	};
	
	private void setupHttpParams()
	{
	}
	
	private void setupConnectionManager()
	{
		cache = new HTTPCache(
			new MemoryCacheStorage(),
			HTTPClientResponseResolver.createMultithreadedInstance());
	}
	
	public HttpJobRunnerCacheJ()
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
		List<Future<?>> status = new ArrayList<Future<?>>(); 
		
		for (HttpJob job : jobs) {
			if (StringUtil.isSimilar(job.getMethod(), "GET")) {
				Future<HttpJob> jobStatus = queue.submit(new GetRequest4(cache, job));
				status.add(jobStatus);
				continue;
			}
		}

		while (! status.isEmpty()) {
			Iterator<Future<?>> i = status.iterator();
			while (i.hasNext()) {
				try {
					Future<?> future = i.next();
					future.get(timeout, TimeUnit.MILLISECONDS);
					i.remove();
				} catch (TimeoutException e) {
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			}
		}

		executor.shutdown();

	}
	
	public void abort()
	{
	}
}

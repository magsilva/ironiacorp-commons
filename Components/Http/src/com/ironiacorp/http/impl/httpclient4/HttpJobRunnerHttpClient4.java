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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;
import com.ironiacorp.http.impl.HttpClient;

public class HttpJobRunnerHttpClient4 extends HttpClient implements HttpJobRunner
{
	private int maxThreadsCount = 3;
	
	private int timeout = 100;

	private ClientConnectionManager cm;
	
	private HttpParams params;
	
	private void setupHttpParams()
	{
		params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(params, "UTF-8");
		HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.14) Gecko/20110301 Fedora/3.6.14-1.fc14 Firefox/3.6.14");
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
		((ThreadSafeClientConnManager) cm).setMaxTotal(200);
		// Increase default max connection per route to 20
		((ThreadSafeClientConnManager) cm).setDefaultMaxPerRoute(20);


	}

	
	public HttpJobRunnerHttpClient4()
	{
		super();
		setupHttpParams();
		setupConnectionManager();
	}
	
	public void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool(maxThreadsCount);
		ExecutorCompletionService<HttpJob> queue = new ExecutorCompletionService<HttpJob>(executor);
		List<Future<?>> workers = new ArrayList<Future<?>>(); 
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		HttpContext context = new BasicHttpContext();
		
		// Cookie configuration
		CookieStore cookieStore = new BasicCookieStore();
	    httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY); 
	    httpClient.setCookieStore(cookieStore);
	    context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

	    // Redirection stratety
	    httpClient.setRedirectStrategy(new DefaultRedirectStrategy());
	    
	    for (HttpJob job : jobs) {
			if (HttpMethod.GET == job.getMethod()) {
				GetRequest request = new GetRequest(httpClient, context, job);
				Future<HttpJob> jobStatus = queue.submit(request);
				workers.add(jobStatus);
				continue;
			}
			if (HttpMethod.POST == job.getMethod()) {
				PostRequest request = new PostRequest(httpClient, context, job);
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

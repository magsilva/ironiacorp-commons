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

package com.ironiacorp.commons.http.httpclient3;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;


public class HttpJobRunner3
{
	private int maxThreadsCount = 3;
	
	public void run()
	{
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		// connectionManager.// maxConnectionsPerHost // maxTotalConnections
        HttpClient httpClient = new HttpClient(connectionManager);

        // http://jakarta.apache.org/httpcomponents/httpclient-3.x/preference-api.html
        HttpClientParams params = httpClient.getParams();
		params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
		params.setParameter("http.socket.timeout", new Integer(60000));
		params.setParameter("http.protocol.content-charset", "UTF-8");
        params.setCookiePolicy(CookiePolicy.RFC_2109);
        
		
        // TODO: Update as leave as HttpJobRunner4
        /*
        List<URI> uris = getURIs();
		ExecutorService executor = Executors.newFixedThreadPool(maxThreadsCount);
		ExecutorCompletionService<File> queue = new ExecutorCompletionService<File>(executor); 
		 for (URI uri : uris) {
			 queue.submit(getMethod(uri, getParameters(), httpClient));
		}
		
		for (int i = 0; i < uris.size(); i++) {
			try {
				File result = queue.take().get();
				processResult(result);
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
			}
		}
		executor.shutdown();
		*/
	}
}

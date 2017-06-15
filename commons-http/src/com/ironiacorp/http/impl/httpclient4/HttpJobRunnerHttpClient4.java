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

import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.config.SocketConfig.Builder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultBHttpClientConnectionFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;
import com.ironiacorp.http.impl.HttpClient;

//TODO: update and fix
public class HttpJobRunnerHttpClient4 extends HttpClient implements HttpJobRunner
{
	private int maxThreadsCount = 3;
	
	private int timeout = 100;
	
	private HttpClientBuilder httpClientBuilder;

	public HttpJobRunnerHttpClient4()
	{
		super();
		setupConnectionManager();
		setupClient();
	}
	
	private void setupConnectionManager()
	{
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory();
		ConnectionConfig cconfig = ConnectionConfig.DEFAULT;
		HttpConnectionFactory<DefaultBHttpClientConnection> connFactory = new DefaultBHttpClientConnectionFactory(cconfig, requestWriterFactory, responseParserFactory);

		DnsResolver dnsResolver = new SystemDefaultDnsResolver();
		SSLContext sslcontext = SSLContexts.createSystemDefault();
		X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();
		RegistryBuilder<ConnectionSocketFactory> socketFactoryRegistryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
		socketFactoryRegistryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
		socketFactoryRegistryBuilder.register("https", SSLConnectionSocketFactory.getSocketFactory());
		Registry<ConnectionSocketFactory> socketFactoryRegistry = socketFactoryRegistryBuilder.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);
		connManager.setMaxTotal(100);
		
		// Setup socket configuration
		Builder socketConfigBuilder = SocketConfig.custom();
		socketConfigBuilder.setTcpNoDelay(true);
		socketConfigBuilder.setSoKeepAlive(true);
		socketConfigBuilder.setSoTimeout(timeout);
		SocketConfig socketConfig = socketConfigBuilder.build();
		connManager.setDefaultSocketConfig(socketConfig);

		// Setup connection configuration
		ConnectionConfig.Builder connectionConfigBuilder = ConnectionConfig.custom();
		connectionConfigBuilder.setMalformedInputAction(CodingErrorAction.IGNORE);
		connectionConfigBuilder.setUnmappableInputAction(CodingErrorAction.IGNORE);
		connectionConfigBuilder.setCharset(Consts.UTF_8);
		ConnectionConfig connectionConfig = connectionConfigBuilder.build();
        connManager.setDefaultConnectionConfig(connectionConfig);
		
        // Use custom cookie store if necessary
        CookieStore cookieStore = new BasicCookieStore();
        
        // Use custom credential provider (if necessary)
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        // Create global request configuration
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // requestConfigBuilder.setCookieSpec(null);
        requestConfigBuilder.setExpectContinueEnabled(false);
        requestConfigBuilder.setStaleConnectionCheckEnabled(true);
        requestConfigBuilder.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC));
        requestConfigBuilder.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC));
        requestConfigBuilder.setSocketTimeout(timeout);
        RequestConfig requestConfig = requestConfigBuilder.build();
        
        httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(connManager);
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
                
	}

	
	private void setupClient()
	{
		/*
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(params, "UTF-8");
		HttpConnectionParams.setConnectionTimeout(params, 100);
		HttpConnectionParams.setSoTimeout(params, 100);
		HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (X11; U; Linux x86_64; r1.9.0.2) Gecko/20121225 Firefox/3.6.14");
		*/
	}
	
	public void run()
	{
		CloseableHttpClient httpClient = httpClientBuilder.build();
		HttpClientContext context = HttpClientContext.create();

		if (concurrency) {
			ExecutorService executor = Executors.newFixedThreadPool(maxThreadsCount);
			ExecutorCompletionService<HttpJob> queue = new ExecutorCompletionService<HttpJob>(executor);
			List<Future<?>> workers = new ArrayList<Future<?>>(); 
	
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
		} else {
			for (HttpJob job : jobs) {
				System.out.println(job.getUri());
					if (HttpMethod.GET == job.getMethod()) {
					GetRequest request = new GetRequest(httpClient, context, job);
					request.call();
				}
				if (HttpMethod.POST == job.getMethod()) {
					PostRequest request = new PostRequest(httpClient, context, job);
					request.call();
				}
				// TODO: job cannot be handled, what to do?
			}
		
		}
	}
	
	public void abort()
	{
		// cm.shutdown();
	}
}

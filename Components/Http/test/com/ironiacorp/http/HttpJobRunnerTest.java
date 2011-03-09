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

package com.ironiacorp.http;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpJobRunner;
import com.ironiacorp.http.HttpMethod;
import com.ironiacorp.http.HttpMethodResult;

/**
 * Test set for HttpJobRunner implementations. All of them must pass this test
 * set.
 */
public abstract class HttpJobRunnerTest
{
	private static Server server;
	
	private static final int HTTP_SERVER_PORT = 12345;

	private static class DefaultHttpGetRequestHandler extends AbstractHandler {
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
		{
			if (response.isCommitted() || baseRequest.isHandled()) {
				return;
			}

			if ("GET".equalsIgnoreCase(request.getMethod())) {
				response.setContentType("text/html;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				ServletOutputStream sos = response.getOutputStream();
				sos.print("<h1>Hello World</h1>");
				sos.flush();
				sos.close();
				baseRequest.setHandled(true);
			}
		}
	}

	private static class DefaultHttpPostRequestHandler extends AbstractHandler {
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
		{
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				response.setContentType("text/html;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println("<h1>Hello World</h1>");
				baseRequest.setHandled(true);
			}
		}
	}
	
	protected HttpJobRunner runner;

	@BeforeClass
	public static void setUpTestSet() throws Exception {
		ArrayList<Handler> handlerTempList = new ArrayList<Handler>();
		HandlerList handlers = new HandlerList();
		handlerTempList.add(new DefaultHttpGetRequestHandler());
		handlerTempList.add(new DefaultHttpPostRequestHandler());
		handlers.setHandlers(handlerTempList.toArray(new Handler[0]));
		server = new Server(HTTP_SERVER_PORT);
		server.setHandler(handlers);
		// server.start();
	}

	@Ignore // TODO: Fix Jetty configuration.
	@Test
	public void testSingle_EmbeddedServer() throws URISyntaxException {
		HttpJob job = new HttpJob(HttpMethod.GET, new URI("http://localhost:" + HTTP_SERVER_PORT + "/index.html"));
		runner.addJob(job);
		runner.run();
		HttpMethodResult result = job.getResult();
		assertNotNull(result);

		String page = new String(result.getContentAsByteArray());
		assertTrue(page.startsWith("<h1>Hello World</h1>"));
	}
	
	@Test
	public void testSingle() throws URISyntaxException {
		HttpJob job = new HttpJob(HttpMethod.GET, new URI(
				"http://www.icmc.usp.br/~magsilva/index.html"));
		runner.addJob(job);
		runner.run();
		HttpMethodResult result = job.getResult();
		assertNotNull(result);

		String page = new String(result.getContentAsByteArray());
		assertTrue(page.startsWith("<!DOCTYPE HTML PUBLIC"));
	}

	@Test
	public void testManyDifferentRequests() throws URISyntaxException {
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.icmc.usp.br/~magsilva/index.html")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.google.com")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.uol.com.br")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.terra.com.br")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.ig.com.br")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.globo.com")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.odiariomaringa.com.br")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.freshmeat.net")));
		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.slashdot.org")));
		runner.run();
	}

	@Test
	public void testSingleWithRedirect() throws URISyntaxException {
		HttpJob job = new HttpJob(HttpMethod.GET, new URI(
				"http://dx.doi.org/10.1109/ITHET.2006.339782"));
		runner.addJob(job);
		runner.run();
		HttpMethodResult result = job.getResult();
		assertNotNull(result);
	}

	/**
	 * If the page supports caching, the results are more dramatic. Check
	 * http://redbot.org/ for cacheable pages.
	 */
	@Test
	public void testManyIdenticalRequests() throws URISyntaxException {
		long beforeTime;
		long afterTime;
		long totalTime1;
		long totalTime2;

		runner.addJob(new HttpJob(HttpMethod.GET, new URI(
				"http://www.freshmeat.net")));
		beforeTime = System.currentTimeMillis();
		runner.run();
		afterTime = System.currentTimeMillis();
		totalTime1 = afterTime - beforeTime;

		for (int i = 0; i < 10; i++) {
			runner.addJob(new HttpJob(HttpMethod.GET, new URI(
					"http://www.freshmeat.net")));
		}
		beforeTime = System.currentTimeMillis();
		runner.run();
		afterTime = System.currentTimeMillis();
		totalTime2 = afterTime - beforeTime;

		assertTrue((5 * totalTime1) > totalTime2);
	}
	
	@Test
	public void testPost() throws Exception
	{
		HttpJob job = new HttpJob(HttpMethod.POST, new URI("http://www.ironiacorp.com/Tests/form_post.php"));
		job.addParameter("city", "Cactusville");
		runner.addJob(job);
		runner.run();
		HttpMethodResult result = job.getResult();
		String text = new String(result.getContentAsByteArray());
		assertTrue(text.contains("Cactusville"));
	}

}
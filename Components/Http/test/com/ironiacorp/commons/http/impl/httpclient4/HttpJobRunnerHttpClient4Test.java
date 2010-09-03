package com.ironiacorp.commons.http.impl.httpclient4;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;


import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.HttpMethodResult;
import com.ironiacorp.http.impl.httpclient4.HttpJobRunner4;

public class HttpJobRunnerHttpClient4Test
{
	@Test
	public void testSingle() throws URISyntaxException 
	{
		HttpJob<ByteArrayOutputStream> job = new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.icmc.usp.br/~magsilva/index.html"));
		HttpJobRunner4 runner = new HttpJobRunner4();
		runner.addJob(job);
		runner.run();
		HttpMethodResult<ByteArrayOutputStream> result = job.getResult();
		assertNotNull(result);
		
		ByteArrayOutputStream baos = result.getContent();
		String page = new String(baos.toString());
		assertTrue(page.startsWith("<!DOCTYPE HTML PUBLIC"));
	}

	@Test
	public void testMultiple() throws URISyntaxException 
	{
		HttpJobRunner4 runner = new HttpJobRunner4();
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.icmc.usp.br/~magsilva/index.html")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.google.com")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.uol.com.br")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.terra.com.br")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.ig.com.br")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.globo.com")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.odiariomaringa.com.br")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.freshmeat.net")));
		runner.addJob(new HttpJob<ByteArrayOutputStream>("GET", new URI("http://www.slashdot.org")));
		runner.run();
	}
}

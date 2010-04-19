package com.ironiacorp.commons.http.impl.httpclient4;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;


import com.ironiacorp.http.HttpJob;
import com.ironiacorp.http.impl.httpcachej.HttpJobRunnerCacheJ;

public class HttpJobRunner4Test
{
	@Test
	public void testSingle() throws URISyntaxException 
	{
		HttpJob job = new HttpJob("GET", new URI("http://www.icmc.usp.br/~magsilva/index.html"));
		HttpJobRunnerCacheJ runner = new HttpJobRunnerCacheJ();
		runner.addJob(job);
		runner.run();
		assertNotNull(job.getResult());
	}

	@Test
	public void testMultiple() throws URISyntaxException 
	{
		HttpJobRunnerCacheJ runner = new HttpJobRunnerCacheJ();
		runner.addJob(new HttpJob("GET", new URI("http://www.icmc.usp.br/~magsilva/index.html")));
		runner.addJob(new HttpJob("GET", new URI("http://www.google.com")));
		runner.addJob(new HttpJob("GET", new URI("http://www.uol.com.br")));
		runner.addJob(new HttpJob("GET", new URI("http://www.terra.com.br")));
		runner.addJob(new HttpJob("GET", new URI("http://www.ig.com.br")));
		runner.addJob(new HttpJob("GET", new URI("http://www.globo.com")));
		runner.addJob(new HttpJob("GET", new URI("http://www.odiariomaringa.com.br")));
		runner.addJob(new HttpJob("GET", new URI("http://www.freshmeat.net")));
		runner.addJob(new HttpJob("GET", new URI("http://www.slashdot.org")));
		runner.run();
	}
}

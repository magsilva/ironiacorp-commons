package com.ironiacorp.ws.discovery.slp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SlpServiceDiscovererTest
{
	private SlpServiceDiscoverer disco;
	
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testDiscovery() throws Exception
	{
		disco.find("cmapV3");
	}

}

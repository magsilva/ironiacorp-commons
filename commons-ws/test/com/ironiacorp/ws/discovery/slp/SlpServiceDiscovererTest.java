package com.ironiacorp.ws.discovery.slp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.ws.discovery.criteria.ScalarPropertyCriterion;

public class SlpServiceDiscovererTest
{
	private SlpServiceDiscoverer disco;
	
	@Before
	public void setUp() throws Exception
	{
		disco = new EclipseSlpManager();
	}

	@Ignore
	@Test
	public void testDiscovery() throws Exception
	{
		ScalarPropertyCriterion criterion1 = new ScalarPropertyCriterion("scope", "cmapdp");
		ScalarPropertyCriterion criterion2 = new ScalarPropertyCriterion("name", "cmapV3");
		
		disco.find(criterion1, criterion2);
	}

}

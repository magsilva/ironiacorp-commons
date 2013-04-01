package com.ironiacorp.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExponentialTest {

	private Exponential exp;

	@Before
	public void setUp() throws Exception {
		exp = new Exponential();
	}

	@Test
	public void testExp3() {
		assertEquals(2.718281828, exp.exp(1, 3), 0.25);
	}

	@Test
	public void testExp8() {
		assertEquals(2.718281828, exp.exp(1, 8), 0.00005);
	}


}

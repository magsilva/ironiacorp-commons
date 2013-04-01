package com.ironiacorp.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.lang.Math;

public class TrigonometryTest {

	private Trigonometry trig;

	@Before
	public void setUp() throws Exception {
		trig = new Trigonometry();
	}

	@Test
	public void testSin() {
		assertEquals(0.5, trig.sin(0.523598776, 20), 0.1);
	}

	@Test
	public void testCos() {
		assertEquals(0.866025404, trig.cos(0.523598776, 30), 0.1);
	}

}

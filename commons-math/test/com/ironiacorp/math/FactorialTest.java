package com.ironiacorp.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FactorialTest {

	private Factorial factorial;

	@Before
	public void setUp() throws Exception {
		factorial = new Factorial();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNegative() {
		factorial.factorial(-1);
	}


	@Test
	public void test0() {
		assertEquals(1, factorial.factorial(0));
	}

	@Test
	public void test1() {
		assertEquals(1, factorial.factorial(1));
	}

	@Test
	public void test2() {
		assertEquals(2, factorial.factorial(2));
	}

	@Test
	public void test3() {
		assertEquals(6, factorial.factorial(3));
	}

	@Test
	public void test4() {
		assertEquals(24, factorial.factorial(4));
	}

	@Test
	public void testManyInSequence() {
		assertEquals(1, factorial.factorial(1));
		assertEquals(2, factorial.factorial(2));
		assertEquals(6, factorial.factorial(3));
		assertEquals(24, factorial.factorial(4));
		assertEquals(120, factorial.factorial(5));
		assertEquals(720, factorial.factorial(6));
	}

	@Test
	public void testManyRandomAccess() {
		assertEquals(1, factorial.factorial(1));
		assertEquals(24, factorial.factorial(4));
		assertEquals(2, factorial.factorial(2));
		assertEquals(720, factorial.factorial(6));
		assertEquals(6, factorial.factorial(3));
		assertEquals(120, factorial.factorial(5));
	}


}

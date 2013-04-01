package com.ironiacorp.number;

import static org.junit.Assert.*;

import org.junit.Test;

public class DecimalNumberTest {

	@Test
	public void test10() {
		DecimalNumber number = new DecimalNumber("10");
		assertEquals(10, number.getLSE());
		assertEquals(10, number.getMSE());
		assertEquals(10, number.getValue());
	}

	@Test
	public void test347() {
		DecimalNumber number = new DecimalNumber("347");
		assertEquals(7, number.getLSE());
		assertEquals(300, number.getMSE());
		assertEquals(347, number.getValue());
	}

	@Test
	public void test3470() {
		DecimalNumber number = new DecimalNumber("3470");
		assertEquals(70, number.getLSE());
		assertEquals(3000, number.getMSE());
		assertEquals(3470, number.getValue());
	}
	
	@Test
	public void test34700() {
		DecimalNumber number = new DecimalNumber("34700");
		assertEquals(700, number.getLSE());
		assertEquals(30000, number.getMSE());
		assertEquals(34700, number.getValue());
	}
	
	@Test
	public void test347001() {
		DecimalNumber number = new DecimalNumber("347001");
		assertEquals(1, number.getLSE());
		assertEquals(300000, number.getMSE());
		assertEquals(347001, number.getValue());
	}
	
	
}

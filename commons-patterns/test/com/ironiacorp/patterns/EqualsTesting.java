package com.ironiacorp.patterns;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class EqualsTesting
{
	private Object o1;
	
	private Object o2;

	public void setObject1(Object o1)
	{
		if (o1 != null) {
			this.o1 = o1;
		}
	}
	
	public void setObject2(Object o2)
	{
		if (o2 != null) {
			this.o2 = o2;
		}
	}
	
	public EqualsTesting(Object o1, Object o2) {
		setObject1(o1);
		setObject2(o2);
	}
	
	@Test
	public void testSimetry()
	{
		assertTrue(o1.equals(o1));
	}

	@Test
	public void testReflexivity()
	{
		assertEquals(o1.equals(o2), o2.equals(o1));
	}

	@Test
	public void testNullity()
	{
		assertFalse(o1.equals(null));
	}

	@Test
	public void testHashCode()
	{
		if (o1.equals(o2)) {
			assertTrue(o1.hashCode() == o2.hashCode());
		}
	}

	
}

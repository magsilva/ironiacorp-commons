package com.ironiacorp.introspector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Test;
import static org.junit.Assert.*;


public class GenericsTest
{

	public class Dummy<T> {
		public Class getGenericParameterType() {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			Type[] genericClasses = genericSuperclass.getActualTypeArguments();
			return (Class) genericClasses[0];
		}
	}
	
	public class DumbDummy extends Dummy<String> {
		
	}

	
	public class SmartDummy<T> extends Dummy<T> {
		
	}

	@Test
	public void testGenerics()
	{
		DumbDummy dummy = new DumbDummy();
		assertEquals(String.class, dummy.getGenericParameterType());
	}
	
	@Test
	public void testGenerics2()
	{
		SmartDummy dummy = new SmartDummy<String>();
		assertEquals(String.class, dummy.getGenericParameterType());
	}
}

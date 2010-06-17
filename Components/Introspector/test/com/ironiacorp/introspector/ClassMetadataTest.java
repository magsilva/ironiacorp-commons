package com.ironiacorp.introspector;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.introspector.reflection.ClassMetadata;
import com.ironiacorp.introspector.reflection.FieldMetadata;
import com.ironiacorp.introspector.reflection.TypeParameterMetadata;

public class ClassMetadataTest
{
	private class GenericTestClass<T>
	{
		public List<T> list;

		public T testMethod() throws Exception
		{
			return null;
		}
	}

	private class TestClass
	{
		public List list;

		public String testMethod() throws Exception
		{
			return null;
		}
	}

	private class TestClass2
	{
		public List<String> list;
	}

	@Before
	public void setUp() throws Exception
	{

	}

	@Test
	public void testField() throws Exception
	{
		// get the basic information
		Field field = TestClass2.class.getDeclaredField("list");
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Class rclas = (Class) ptype.getRawType();
		assertEquals(List.class, rclas);
		
		// list the type variables of the base class
		TypeVariable[] tvars = rclas.getTypeParameters();
		for (int i = 0; i < tvars.length; i++) {
			TypeVariable tvar = tvars[i];
		}
		
		// list the actual type arguments
	    Type[] targs = ptype.getActualTypeArguments();
	    for (int j = 0; j < targs.length; j++) {
	        Class tclas = (Class)targs[j];
	        assertEquals(String.class, tclas);
	    }
	}

	@Test
	public void testField2() throws Exception
	{
		// get the basic information
		Field field = TestClass2.class.getDeclaredField("list");
		FieldMetadata fm = new FieldMetadata(field);
				
		assertEquals(List.class, fm.getType());
		
		TypeParameterMetadata[] tpm = fm.getTypeParameters();
		assertEquals(String.class, tpm[0].getArguments()[0]);
	}

	
	@Test
	public void testMethodGenericExceptionType() throws Exception
	{
		GenericTestClass<String> t = new GenericTestClass<String>();
		Method m = t.getClass().getMethod("testMethod", null);
		assertArrayEquals(m.getExceptionTypes(), m.getGenericExceptionTypes());
	}

	@Test
	public void testMethodExceptionType() throws Exception
	{
		TestClass t = new TestClass();
		Method m = t.getClass().getMethod("testMethod", null);
		assertArrayEquals(m.getExceptionTypes(), m.getGenericExceptionTypes());
	}

	@Test
	public void testGetType()
	{
		GenericTestClass<String> t = new GenericTestClass<String>();
		ClassMetadata metadata = new ClassMetadata(t.getClass());
		assertEquals(String.class, metadata.getGenericTypes()[0]);
	}

	@Test
	public void testStringType()
	{
		Class clazz = "Teste".getClass();
		ClassMetadata metadata = new ClassMetadata(clazz);
	}

	@Test
	public void testSimpleGenericType()
	{
		GenericTestClass<String> test = new GenericTestClass<String>();
		Class clazz = test.getClass();
		ClassMetadata metadata = new ClassMetadata(clazz);
		assertEquals(String.class, metadata.getGenericTypes()[0]);
	}
}
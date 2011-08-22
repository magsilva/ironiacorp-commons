/*
 * Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.introspector;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.datastructure.array.ArrayUtil;

public class ObjectIntrospectorTest
{
	private ObjectIntrospector oi;
	
	private DummyBean bean;
	
	private Map<String, Object> goodBeanMapping;
	private Map<String, Object> badBeanMapping;

	
	public class DummyBean
	{
		private static final String DEFAULT_NAME = "John Due";
		private static final int DEFAULT_AGE = 60;
			
		public static final String NAME_FIELD = "name";
		public static final String AGE_FIELD = "age";

		private String name;
		private int age;
		
		public DummyBean()
		{
			name = DEFAULT_NAME;
			age = DEFAULT_AGE;
		}
		
		public int getAge()
		{
			return age;
		}

		public void setAge(int age)
		{
			this.age = age;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}


	@Before
	public void setUp() throws Exception
	{
		oi = new ObjectIntrospector();
		
		bean = new DummyBean();

		goodBeanMapping = new HashMap<String, Object>();
		goodBeanMapping.put(DummyBean.NAME_FIELD, DummyBean.DEFAULT_NAME);
		goodBeanMapping.put(DummyBean.AGE_FIELD, DummyBean.DEFAULT_AGE);
		
		badBeanMapping = new HashMap<String, Object>();
		badBeanMapping.put("dkljlsa", DummyBean.DEFAULT_NAME);
		badBeanMapping.put("j2309j9", DummyBean.DEFAULT_AGE);
	}

	@Test
	public void testIgnoredProperties()
	{
		bean = new DummyBean();
		oi.setObject(bean);
		Object[] expectedIgnoredProperties = oi.getPropertiesNameFromMethod();
		Object[] ignoredProperties = oi.IGNORED_PROPERTIES;
		assertTrue(ArrayUtil.equalIgnoreOrder(expectedIgnoredProperties, ignoredProperties));
	}
	
	@Test
	public void testGetPropertyValue()
	{
		bean = new DummyBean();
		bean.setName("Test 1 2 3");
		oi.setObject(bean);
		Object result = oi.getProperty("name");
		assertEquals("Test 1 2 3", result);
	}
	
	@Test
	public void testMapBean1()
	{
		oi.setObject(bean);
		Map mapping = oi.map();
		assertEquals(goodBeanMapping, mapping);
	}

	@Test
	public void testMapBean2()
	{
		oi.setObject(bean);
		assertFalse(badBeanMapping.equals(oi.map()));
	}

	
	@Ignore
	@Test
	public void testMapBeanUsingFieldsObject()
	{
		oi.setObject(bean);
		Map mapping = oi.map();
		assertEquals(goodBeanMapping, mapping);
	}
}

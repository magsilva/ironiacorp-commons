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


package com.ironiacorp.persistence.dao.ideais;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.persistence.dao.ideais.DbMappings;
import com.ironiacorp.persistence.dao.ideais.Property;

public class DbMappingTest
{
	private DbMappings dbmap;
	
	private DummyBean bean;
	private AnnotatedDummyBean annotatedBean;
	
	private Map<String, Object> goodBeanMapping;
	private Map<String, Object> badBeanMapping;

	public class AnnotatedDummyBean
	{
		private static final String DEFAULT_NAME = "John Due";
		private static final int DEFAULT_AGE = 60;
		
		@Property(value="name")
		public String name;

		@Property(value="age")
		public int age;
		
		public AnnotatedDummyBean()
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
		dbmap = new DbMappings();
		
		bean = new DummyBean();
		annotatedBean = new AnnotatedDummyBean();

		goodBeanMapping = new HashMap<String, Object>();
		goodBeanMapping.put(DummyBean.NAME_FIELD, DummyBean.DEFAULT_NAME);
		goodBeanMapping.put(DummyBean.AGE_FIELD, DummyBean.DEFAULT_AGE);
		
		badBeanMapping = new HashMap<String, Object>();
		badBeanMapping.put("dkljlsa", DummyBean.DEFAULT_NAME);
		badBeanMapping.put("j2309j9", DummyBean.DEFAULT_AGE);
	}
}

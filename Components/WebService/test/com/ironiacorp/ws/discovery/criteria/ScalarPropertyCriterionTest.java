/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.ws.discovery.criteria;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScalarPropertyCriterionTest
{
	public class Dummy {
		private String name;
		
		private int number;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getNumber()
		{
			return number;
		}

		public void setNumber(int number)
		{
			this.number = number;
		}
	}
	
	private ScalarPropertyCriterion<Object> criterion;
	
	@Before
	public void setUp() throws Exception
	{
		criterion = new ScalarPropertyCriterion<Object>();
	}

	@Test
	public void testSatisfies_Success()
	{
		Dummy dummy = new Dummy();
		dummy.setName("Test");
		dummy.setNumber(123);
		criterion.setPropertyName("name");
		criterion.setPropertyValue("Test");
		assertTrue(criterion.satisfies(dummy));
	}

	@Test
	public void testSatisfies_Fail_DifferentValue()
	{
		Dummy dummy = new Dummy();
		dummy.setName("Abc");
		dummy.setNumber(123);
		criterion.setPropertyName("name");
		criterion.setPropertyValue("Test");
		assertFalse(criterion.satisfies(dummy));
	}

	@Test
	public void testSatisfies_Fail_Null()
	{
		criterion.setPropertyName("name");
		criterion.setPropertyValue("Test");
		assertFalse(criterion.satisfies(null));
	}
}

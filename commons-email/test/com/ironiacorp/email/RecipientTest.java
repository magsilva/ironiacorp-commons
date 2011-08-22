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

package com.ironiacorp.email;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RecipientTest
{
	private Recipient recipient;
	
	@Before
	public void setUp() throws Exception
	{
		recipient = new Recipient();
	}

	@Test
	public void testToString()
	{
		recipient.setName("Marco Aurélio Graciotto Silva");
		recipient.setEmail("magsilva@ironiacorp.com");
		assertEquals("Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>", recipient.toString());
	}

	@Test
	public void testToString_Null()
	{
		assertNull(recipient.toString());
	}
	
	@Test
	public void testToString_Empty()
	{
		recipient.setName("");
		recipient.setEmail("");
		assertNull(recipient.toString());
	}
	
	@Test
	public void testToString_NoEmail()
	{
		recipient.setName("Marco Aurélio Graciotto Silva");
		assertNull(recipient.toString());
	}
	
	@Test
	public void testToString_NoName()
	{
		recipient.setEmail("magsilva@ironiacorp.com");
		assertEquals("magsilva@ironiacorp.com", recipient.toString());
	}
}

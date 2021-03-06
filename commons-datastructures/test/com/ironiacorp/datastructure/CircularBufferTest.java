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

package com.ironiacorp.datastructure;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CircularBufferTest
{
	private CircularBuffer<String> buffer;
	
	@Before
	public void setUp() throws Exception
	{
		buffer = new CircularBuffer<String>(2);
	}

	@Test
	public void testAdd()
	{
		ArrayList<String> result;
		
		result = buffer.add("software");
		assertNull(result);
		result = buffer.add("testing");
		assertEquals("software", result.get(0));
		assertEquals("testing", result.get(1));
	}

	@Test
	public void testReset()
	{
		ArrayList<String> result;
		
		result = buffer.add("software");
		assertNull(result);
		
		result = buffer.reset();
		assertEquals("software", result.get(0));
		
		result = buffer.add("structural");
		assertNull(result);
		
		result = buffer.add("testing");
		assertEquals("structural", result.get(0));
		assertEquals("testing", result.get(1));
	}
}

/*
 * Copyright (C) 2012 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

public class StackTest
{
	private Stack<String> stack;

	@Before
	public void setUp()
	{
		stack = new Stack<String>();
	}

	@Test
	public void testPush() {
		stack.push("Test123");
		assertEquals("Test123", stack.peek());
	}

	@Test(expected=EmptyStackException.class)
	public void testPop_Empty() {
		stack.pop();
	}

	@Test
	public void testPop_PopEmpty() {
		stack.push("Test123");
		assertEquals("Test123", stack.pop());
		assertTrue(stack.isEmpty());
		try {
			stack.pop();
			fail();
		} catch (EmptyStackException ese) {}
	}

	@Test
	public void testPop_PopPop() {
		stack.push("Test1");
		stack.push("Test2");
		assertEquals("Test2", stack.pop());
		assertEquals("Test1", stack.pop());
		assertTrue(stack.isEmpty());
	}


	@Test
	public void testPeek() {
		stack.push("Test123");
		assertEquals("Test123", stack.peek());
	}

	@Test(expected=EmptyStackException.class)
	public void testPeekEmpty() {
		stack.peek();
	}

	@Test
	public void testIsEmpty() {
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testIsEmpty_False()
	{
		stack.push("Test123");
		assertFalse(stack.isEmpty());
	}
}

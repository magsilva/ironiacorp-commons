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

import org.junit.Before;
import org.junit.Test;

public class StackBenchmark
{
	private Stack<Integer> ironiacorpStack;

	private java.util.Stack<Integer> javaStack;

	private static final int limit = 1000000;


	@Before
	public void setUp()
	{
		ironiacorpStack = new Stack<Integer>();
		javaStack = new java.util.Stack<Integer>();
	}

	@Test
	public void testPushBatchPopBatch_IroniaCorp() {
		for (int i = 0; i < limit; i++) {
			ironiacorpStack.push(new Integer(i));
		}
		for (int i = 0; i < limit; i++) {
			ironiacorpStack.pop();
		}
	}

	@Test
	public void testPushBatchPopBatch_Java() {
		for (int i = 0; i < limit; i++) {
			javaStack.push(new Integer(i));
		}
		for (int i = 0; i < limit; i++) {
			javaStack.pop();
		}
	}


	@Test
	public void testPushPop_Ironiacorp() {
		for (int i = 0; i < limit; i++) {
			ironiacorpStack.push(new Integer(i));
			ironiacorpStack.pop();
		}
	}

	@Test
	public void testPushPop_Java() {
		for (int i = 0; i < limit; i++) {
			javaStack.push(new Integer(i));
			javaStack.pop();
		}
	}


	@Test
	public void testPushPeek_Ironiacorp() {
		for (int i = 0; i < limit; i++) {
			ironiacorpStack.push(new Integer(i));
			ironiacorpStack.peek();
		}
	}

	@Test
	public void testPushPeek_Java() {
		for (int i = 0; i < limit; i++) {
			javaStack.push(new Integer(i));
			javaStack.peek();
		}
	}
}

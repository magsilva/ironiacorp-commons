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

import java.util.EmptyStackException;

/**
 * Stack of elements (LIFO data structure).
 *
 * @param <T> Type of class that is supported by the stack.
 */
public class Stack<T>
{
	private class StackNode {
		T object;
		StackNode previous;
	}

	private StackNode top;

	public Stack()
	{
		top = null;
	}

	public void push(T object)
	{
		StackNode node = new StackNode();
		node.object = object;
		node.previous = top;
		top = node;
	}

	public T pop()
	{
		T result;
		if (top == null) {
			throw new EmptyStackException();
		}
		result = top.object;
		top = top.previous;
		return result;
	}

	public T peek()
	{
		if (top == null) {
			throw new EmptyStackException();
		}
		return top.object;
	}

	public boolean isEmpty()
	{
		return (top == null);
	}
}

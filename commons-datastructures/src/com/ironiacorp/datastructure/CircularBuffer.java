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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CircularBuffer<T> {

	
	private LinkedList<T> buffer;
	
	private int size;
	
	public CircularBuffer(int n)
	{
		buffer = new LinkedList<T>();
		size = n;
	}

	public ArrayList<T> add(T element)
	{
		ArrayList<T> result = null;
		buffer.addLast(element);
		if (buffer.size() == size) {
			result = dumpBuffer(size);
			buffer.removeFirst();
		}
		
		return result;
	}
	
	public ArrayList<T> reset()
	{
		ArrayList<T> result = null;
		if (buffer.size() != 0) {
			result = dumpBuffer(buffer.size());
		}
		buffer.clear();
		return result;
	}
	
	private ArrayList<T> dumpBuffer(int size)
	{
		ArrayList<T> dump = new ArrayList<T>(size);
		dump.addAll(buffer);
		return dump;
	}
}

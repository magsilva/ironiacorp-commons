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

import java.util.LinkedList;

public class StringCircularBuffer {

	private static final String DEFAULT_WORD_SEPARATOR = " ";
	
	private String separator;
	
	private LinkedList<String> buffer;
	
	private int size;
	
	private StringBuilder ngramBuffer;

	public StringCircularBuffer(int n)
	{
		buffer = new LinkedList<String>();
		size = n;
		ngramBuffer = new StringBuilder();
		separator = DEFAULT_WORD_SEPARATOR;
	}

	public String add(String word)
	{
		String ngram = null;

		buffer.addLast(word);
		if (buffer.size() == size) {
			ngram = dumpBuffer(size);
			ngramBuffer.setLength(0);
			buffer.removeFirst();
		}
		
		return ngram;
	}
	
	public String reset()
	{
		String result = null;
		if (buffer.size() != 0) {
			result = dumpBuffer(buffer.size());
		}
		buffer.clear();
		ngramBuffer.setLength(0);
		
		return result;
	}
	
	public void setSeparator(String separator)
	{
		this.separator = separator;
	}
	
	private String dumpBuffer(int size)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(buffer.get(i));
			if (i != (size - 1)) {
				sb.append(separator);
			}
		}
		
		return sb.toString();
	}
}

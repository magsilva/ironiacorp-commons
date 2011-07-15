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

package com.ironiacorp.errorhandler;

import java.io.Serializable;

/**
 * Text range description for plain text file.
 */
public class PlainTextRange implements Serializable
{
	/**
	 * Version of this class (the date its schema has been last modified).
	 */
	private static final long serialVersionUID = 20110715L;
	
	/**
	 * Range line start.
	 */
	private int lineStart;
	
	/**
	 * Range column start.
	 */
	private int columnStart;

	/**
	 * Range line end.
	 */
	private int lineEnd;

	/**
	 * Range column end.
	 */
	private int columnEnd;

	
	private void checkNonNegativeNumber(int number)
	{
		if (number < 0) {
			throw new IllegalArgumentException("Cannot set a negative number");
		}
	}
	
	public int getLineStart()
	{
		return lineStart;
	}

	public void setLineStart(int lineStart)
	{
		checkNonNegativeNumber(lineStart);
		this.lineStart = lineStart;
	}

	public int getColumnStart()
	{
		return columnStart;
	}

	public void setColumnStart(int columnStart)
	{
		checkNonNegativeNumber(columnStart);
		this.columnStart = columnStart;
	}

	public int getLineEnd()
	{
		return lineEnd;
	}

	public void setLineEnd(int lineEnd)
	{
		checkNonNegativeNumber(lineEnd);
		this.lineEnd = lineEnd;
	}

	public int getColumnEnd()
	{
		return columnEnd;
	}

	public void setColumnEnd(int columnEnd)
	{
		checkNonNegativeNumber(columnEnd);
		this.columnEnd = columnEnd;
	} 
	
	/**
	 * Create a text with the text range. Format:
	 * "(" [line start] "," [column start] ":" [line end] "," [column end] ")"
	 * 
	 * @return Text range as plain text.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(getLineStart());
		sb.append(",");
		sb.append(getColumnStart());
		sb.append(":");
		sb.append(getLineEnd());
		sb.append(",");
		sb.append(getColumnEnd());
		sb.append(")");
		
		return sb.toString();
	}
}

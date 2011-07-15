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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Error location that describes a line range within a plain text file.
 */
public class PlainTextErrorLocation implements ErrorLocation
{
	/**
	 * Version of this class (the date its schema has been last modified).
	 */
	private static final long serialVersionUID = 20110715L;

	/**
	 * File which has the error.
	 */
	private File file;
	
	
	/**
	 * Line ranges where the error is located.
	 */
	private List<PlainTextRange> ranges;

	/**
	 * Create a new error location description for a file.
	 */
	public PlainTextErrorLocation()
	{
		ranges = new ArrayList<PlainTextRange>();
	}
	
	/**
	 * Get the name of the file where the error is in.
	 * 
	 * @return Filename.
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * Set the file where the error was found.
	 * 
	 * @param file File with an error.
	 */
	public void setFile(File file)
	{
		if (file == null) {
			throw new IllegalArgumentException("Invalid filename", new NullPointerException());
		}
		this.file = file;
	}
	
	/**
	 * Set the file where the error was found.
	 * 
	 * @param file File with an error.
	 */
	public void setFile(String filename)
	{
		if (filename == null) {
			throw new IllegalArgumentException("Invalid filename", new NullPointerException());
		}
		this.file = new File(filename);
	}

	/**
	 * Get the line ranges where the error was found.
	 * 
	 * @return Line ranges (possibly also with column information).
	 */
	public List<PlainTextRange> getRanges()
	{
		return ranges;
	}

	/**
	 * Add a range where the error was found.
	 * 
	 * @param range Rage (line and column) where the error was found.
	 */
	public void addRange(PlainTextRange range)
	{
		ranges.add(range);
	}
	
	/**
	 * Create a text with the error location. Format:
	 * [filename] "[" [range] (", " [range])* "]"
	 * 
	 * @return Text error location as a plain text.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(file.getAbsolutePath());
		
		sb.append("[");
		Iterator<PlainTextRange> i = ranges.iterator();
		while (i.hasNext()) {
			PlainTextRange range = i.next();
			sb.append(range.toString());
			if (i.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");

		return sb.toString();
	}
}

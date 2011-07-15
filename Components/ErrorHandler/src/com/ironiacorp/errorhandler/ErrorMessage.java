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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ErrorMessage implements Serializable
{
	/**
	 * Version of this class (the date its schema has been last modified).
	 */
	private static final long serialVersionUID = 20110715L;

	/**
	 * Error message identifier generator. It must be atomic as it must be unique for a given
	 * application.
	 */
	private static AtomicInteger idGenerator = new AtomicInteger();

	/**
	 * Error message id.
	 */
	private final int id;
	
	/**
	 * Error description.
	 */
	private String description;
	
	/**
	 * Locations of the error (mostly it is a PlainTextErrorLocation, but it could be anything else,
	 * such as the 3D position of an object within a virtual environment model).
	 */
	private List<ErrorLocation> locations = new ArrayList<ErrorLocation>();

	/**
	 * Create a new error message.
	 */
	public ErrorMessage()
	{
		id = idGenerator.getAndIncrement();
	}
	
	/**
	 * Get the error description.
	 * 
	 * @return Error description.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Set the error description.
	 * 
	 * @param description Error description.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Get a list of error location's descriptors.
	 * 
	 * @return List of locations related to the current error.
	 */
	public List<ErrorLocation> getLocation()
	{
		return locations;
	}

	/**
	 * Add an error location to the current error.
	 * 
	 * @param location Error location.
	 */
	public void addLocation(ErrorLocation location)
	{
		if (location == null) {
			throw new IllegalArgumentException("Invalid error location", new NullPointerException());
		}
		locations.add(location);
	}

	/**
	 * Get the error message id (it is unique for a given application).
	 * 
	 * @return Error message id.
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Generate a textual representation for the error message. The format is:
	 * [id] "-" "{" [location] ("," [location])*  "}" "-" [description]
	 * 
	 * @return Error message.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(" - ");
		
		sb.append("{");
		Iterator<ErrorLocation> i = locations.iterator();
		while (i.hasNext()) {
			ErrorLocation location = i.next();
			sb.append(location.toString());
			if (i.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("}");
		
		sb.append(" - ");
		sb.append(description);
		
		return sb.toString();
	}
}


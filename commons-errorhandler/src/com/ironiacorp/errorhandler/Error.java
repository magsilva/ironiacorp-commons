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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Application error (thrown as an exception).
 */
public class Error extends RuntimeException
{
	/**
	 * Version of this class (the date its schema has been last modified).
	 */
	private static final long serialVersionUID = 20110715L;

	/**
	 * Object that is accountable for the unexpected error. This object
	 * is usually the one that created the error message.
	 */
	private Object object;
	
	/**
	 * Error messages.
	 */
	private List<ErrorMessage> messages;
	
	/**
	 * Configure the accountable object
	 * 
	 * @param object Accountable object.
	 * @throws IllegalArgumentException if it's an invalid object (null).
	 */
	public void setObject(Object object)
	{
		if (object == null) {
			throw new IllegalArgumentException("Invalid object (if you are in doubt, simply use 'this').", new NullPointerException());
		}
		this.object = object;
	}
	
	/**
	 * Configure the error message.
	 * 
	 * @param message Error message.
	 * @throws IllegalArgumentException if it's an invalid message (null) or without a proper implementation of
	 * 'toString'.
	 */
	public void setMessage(ErrorMessage message)
	{
		if (message == null || message.toString() == null) {
			throw new IllegalArgumentException("Invalid error message", new NullPointerException());
		}
		
		messages.add(message);
	}


	/**
	 * Create a new application error exception.
	 * 
	 * @param object Accountable object (usually the object where the exception was first captured).
	 */
	public Error(Object object)
	{
		setObject(object);
		messages = new ArrayList<ErrorMessage>(1);
	}
	
	/**
	 * Create a new application error exception.
	 * 
	 * @param object Accountable object (usually the object where the exception was first captured).
	 * @param message Error message.
	 */
	public Error(Object object, ErrorMessage message)
	{
		this(object);
		setMessage(message);
	}

	/**
	 * Get simple error message.
	 * 
	 * @return Simple error message.
	 */
	@Override
	public String getMessage()
	{
		StringBuilder sb = new StringBuilder();
		Iterator<ErrorMessage> i = messages.iterator();
		while (i.hasNext()) {
			ErrorMessage e = i.next();
			sb.append(e.toString());
			if (i.hasNext()) {
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Get detailed error message.
	 * 
	 * @return (Full) error message.
	 */
	public ErrorMessage getErrorMessage()
	{
		if (messages.size() == 0) {
			return null;
		}
		
		return messages.get(0);
	}
	
	/**
	 * Get detailed error message.
	 * 
	 * @return (Full) error message.
	 */
	public List<ErrorMessage> getErrorMessages()
	{
		return messages;
	}
	
	/**
	 * Get object accountable for this error.
	 * 
	 * @return Accountable object.
	 */
	public Object getAccountableObject()
	{
		return object;
	}
}

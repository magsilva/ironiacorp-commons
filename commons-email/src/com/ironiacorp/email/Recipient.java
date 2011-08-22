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


package com.ironiacorp.email;

import com.ironiacorp.string.StringUtil;

/**
 * Recipient of an email.
 */
public class Recipient
{
	public enum Visibility
	{
		TO,
		CC,
		BCC
	}

	private static final String EMAIL_DELIMITER_START = "<";

	private static final String EMAIL_DELIMITER_END = ">";

	private String name;
	
	private String email;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name.trim();
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email.trim();
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipient other = (Recipient) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		if (email == null || StringUtil.isEmpty(email)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		if (name != null && ! StringUtil.isEmpty(name)) {
			sb.append(name);
			sb.append(" ");
			sb.append(EMAIL_DELIMITER_START);
			sb.append(email);
			sb.append(EMAIL_DELIMITER_END);
		} else {
			sb.append(email);
		}		
		
		return sb.toString();
	}
}

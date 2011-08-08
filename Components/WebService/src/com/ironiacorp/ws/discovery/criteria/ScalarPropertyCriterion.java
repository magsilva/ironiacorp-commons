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

package com.ironiacorp.ws.discovery.criteria;

import com.ironiacorp.introspector.ObjectIntrospector;

public class ScalarPropertyCriterion<T> implements Criterion<T>
{
	private String propertyName;
	
	private Object propertyValue;
	
	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public Object getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	@Override
	public boolean satisfies(T subject)
	{
		ObjectIntrospector oi = new ObjectIntrospector();
		oi.setObject(subject);
		Object value = oi.getProperty(propertyName);
	
		return (propertyValue == value) || propertyValue.equals(value);
	}
}

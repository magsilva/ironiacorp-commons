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


package com.ironiacorp.introspector;

import java.lang.reflect.Method;

public class MethodIntrospector
{
	public enum BeanMethodType {
		GETTER("get"),
		SETTER("set");
		
		public final String prefix;
		
		BeanMethodType(String prefix)
		{
			this.prefix = prefix;
		}
	}
	
	private Method method;
	
	public Method getMethod()
	{
		return method;
	}

	public void setMethod(Method method)
	{
		this.method = method;
	}

	
	public String getPropertyName(BeanMethodType type)
	{
		String property = method.getName();

		if (! property.startsWith(type.prefix)) {
			return null;
		}

		property = property.substring(type.prefix.length());
		property = property.substring(0, 1).toLowerCase() + property.substring(1);
		
		return property;
	}

	
	public String getPropertyName()
	{
		String property = getPropertyName(BeanMethodType.GETTER);
		if (property == null) {
			property = getPropertyName(BeanMethodType.SETTER);
		}
		return property;
	}
}

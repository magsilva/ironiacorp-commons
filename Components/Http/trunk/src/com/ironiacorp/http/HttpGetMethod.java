/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Copyright (C) 2009 Marco Aurélio Graciotto Silva <magsilva@icmc.usp.br>
 */

package com.ironiacorp.http;

import java.net.URI;

import com.ironiacorp.string.StringUtil;

/**
 * HTTP GET request descriptor.
 */
public class HttpGetMethod implements HttpMethod
{
	public boolean checkHttpJob(HttpJob job)
	{
		boolean result = true;
		result &= checkName(job.getMethod());
		result &= checkParameters(job.getParameters());
		
		return result;
	}
	
	public boolean checkName(String name)
	{
		return StringUtil.isSimilar("GET", name);
	}

	public boolean checkParameters(Object... parameters)
	{
		if (parameters.length != 1) {
			return false;	
		}
		
		if (parameters[0] instanceof URI) {
			return true;
		}
		
		return false;
	}
}

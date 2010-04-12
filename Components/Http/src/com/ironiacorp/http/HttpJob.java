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

/**
 * An HTTP job is a descriptor for an HTTP operation, i.e., the HTTP
 * request type and its parameters.
 */

public class HttpJob
{
	/**
	 * HTTP method name.
	 */
	private String method;
	
	/**
	 * HTTP method parameters.
	 */
	private Object[] parameters;
	
	/**
	 * Job result.
	 */
	private HttpMethodResult result;
	
	
	private boolean saveContentToFile = false;
		
	
	public boolean isSaveContentToFile()
	{
		return saveContentToFile;
	}

	public void setSaveContentToFile(boolean saveContentToFile)
	{
		if (result != null) {
			throw new IllegalArgumentException("Cannot save to file after running the job");
		}
		
		this.saveContentToFile = saveContentToFile;
	}

	public HttpJob(String method, Object... parameters)
	{
		this.method = method;
		this.parameters = parameters.clone();
	}
	
	public String getMethod()
	{
		return method;
	}

	public Object getParameter(int i)
	{
		return parameters[i];
	}
	
	public Object[] getParameters()
	{
		return parameters;
	}

	public HttpMethodResult getResult()
	{
		return result;
	}

	public void setResult(HttpMethodResult response)
	{
		this.result = response;
	}
}
